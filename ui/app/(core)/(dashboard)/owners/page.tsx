"use client";

import Button from "@/components/Button";
import Card from "@/components/Card";
import withToast from "@/components/HOCs/withToast";
import Input from "@/components/Input";
import { ToastProps } from "@/components/Toast";
import { getOwner } from "@/libs/action/owner";
import { Owner, Pet } from "@/libs/model";
import { submitOwner } from "app/(core)/(dashboard)/owners/actions";
import { useRouter, useSearchParams } from "next/navigation";
import { startTransition, useActionState, useEffect, useMemo, useState } from "react";
import { twMerge } from "tailwind-merge";

const OwnerDetailPage = ({ setToast }: { setToast: (config: ToastProps) => void }) => {
  const { push, replace } = useRouter();

  const [owner, setOwner] = useState<Owner>();
  const [deletePets, setDeletePets] = useState<Omit<Pet, "owner">[]>([]);

  const searchParams = useSearchParams();
  const id = searchParams.get("id");

  const [state, formAction, isPending] = useActionState(submitOwner, undefined);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const formData = new FormData(e.currentTarget);
    if (id && !Number.isNaN(id)) {
      formData.append("id", id);
      formData.append("pets", JSON.stringify(deletePets));
    }

    startTransition(() => {
      formAction(formData);
    });
  };

  const handleRemovePet = (id: number) => {
    if (owner) {
      const newPets = owner.pets.filter((s) => s.id !== id);
      setOwner({
        ...owner,
        pets: newPets,
      });
      const newDeletePets = [...deletePets, owner.pets.find((s) => s.id === id)].flatMap((s) => (s ? s : []));
      setDeletePets(newDeletePets);
    }
  };

  useEffect(() => {
    (async () => {
      if (id) {
        const ownerData = await getOwner(id);
        if (!ownerData) {
          replace("/owners");

          return;
        }
        setOwner(ownerData);
      }
    })();
  }, [id]);

  useEffect(() => {
    if (state?.message) {
      setToast({
        open: true,
        message: state.message,
      });
    }

    if (state?.isSuccess) {
      push("/owners/list");
    }
  }, [state]);

  return id && !owner ? (
    <>Loading...</>
  ) : (
    <Card header={owner ? `Edit Owner: ${owner.name}` : "New Owner"}>
      <form className="max-w-md" onSubmit={handleSubmit}>
        <div className="flex z-0 w-full mb-5 group items-center gap-5">
          <div className="block mb-2 font-medium text-gray-900 dark:text-white w-[4rem]">Name</div>
          <Input
            type="text"
            name="name"
            required
            classNames="w-full"
            defaultValue={state?.data?.name || owner?.name}
            errors={state?.errors?.name}
          />
        </div>
        <div className="flex z-0 w-full mb-5 group items-center gap-5">
          <div className="block mb-2 font-medium text-gray-900 dark:text-white w-[4rem]">Phone</div>
          <Input
            type="text"
            name="phone"
            required
            classNames="w-full"
            defaultValue={state?.data?.phone || owner?.phone}
            errors={state?.errors?.phone}
          />
        </div>
        <div className="flex z-0 w-full mb-5 group items-start gap-8">
          <div className="block mb-2 font-medium text-gray-900 dark:text-white w-[4rem]">Pets</div>
          <div className={twMerge("flex flex-wrap gap-2 p-3 w-full border rounded min-h-[8rem]")}>
            {owner?.pets.map((s) => (
              <span
                key={s.id}
                onClick={() => handleRemovePet(s.id)}
                className="max-w-max max-h-max relative bg-blue-100 text-blue-800 text-sm font-medium me-2 px-3 py-0.5 rounded dark:bg-blue-900 dark:text-blue-300"
              >
                {s.name}
                <div className="cursor-pointer absolute inline-flex items-center justify-center w-4 h-4 text-[0.5rem] font-bold text-white bg-red-500 border-2 border-white rounded-full -top-2 -end-2 dark:border-gray-900 hover:bg-red-700">
                  X
                </div>
              </span>
            ))}
          </div>
        </div>
        <div className="flex gap-5 grow justify-end">
          <Button type="button" outline onClick={() => push("/owners/list")}>
            Cancel
          </Button>
          <Button disabled={isPending} loading={isPending} type="submit">
            Submit
          </Button>
        </div>
      </form>
    </Card>
  );
};

const WithToastPage = withToast(OwnerDetailPage);

export default WithToastPage;
