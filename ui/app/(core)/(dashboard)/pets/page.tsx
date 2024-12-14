"use client";

import Button from "@/components/Button";
import Card from "@/components/Card";
import Dropdown, { DropdownOption } from "@/components/Dropdown";
import withToast from "@/components/HOCs/withToast";
import Input from "@/components/Input";
import { ToastProps } from "@/components/Toast";
import { getOwners } from "@/libs/action/owner";
import { getPet, submitPet } from "@/libs/action/pet";
import { Owner, Pet } from "@/libs/model";
import { useRouter, useSearchParams } from "next/navigation";
import { startTransition, useActionState, useEffect, useMemo, useState } from "react";

const PetDetailPage = ({ setToast }: { setToast: (config: ToastProps) => void }) => {
  const { push, replace } = useRouter();

  const [pet, setPet] = useState<Pet>();
  const [owners, setOwners] = useState<Owner[]>();
  const [selectedOwner, setSelectedOwner] = useState<DropdownOption>();

  const searchParams = useSearchParams();
  const id = searchParams.get("id");

  const [state, formAction, isPending] = useActionState(submitPet, undefined);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const formData = new FormData(e.currentTarget);
    if (!formData.get("weight")?.toString().trim().length) {
      formData.delete("weight");
    }
    if (!formData.get("age")?.toString().trim().length) {
      formData.delete("age");
    }

    if (id && !Number.isNaN(id)) {
      formData.append("id", id);
    }

    if (!selectedOwner || selectedOwner.id === "-") {
      formData.delete("ownerId");
    } else {
      formData.set("ownerId", selectedOwner.id);
    }

    startTransition(() => {
      formAction(formData);
    });
  };

  const ownerOptions: DropdownOption[] = useMemo(() => {
    return [
      {
        id: "-",
        value: "Choose Owner",
      },
      ...(owners || []).map((s) => ({
        id: String(s.id),
        value: s.name,
      })),
    ];
  }, [owners]);

  useEffect(() => {
    const option = ownerOptions.find((s) => s.id === String(pet?.owner?.id || "")) || ownerOptions[0];
    setSelectedOwner(option);
  }, [ownerOptions]);

  useEffect(() => {
    (async () => {
      if (id) {
        const { isSuccess, data } = await getPet(id);
        if (!isSuccess) {
          replace("/pets");

          return;
        }
        setPet(data);

        const { isSuccess: ownerIsSuccess, data: ownersData, error } = await getOwners();
        if (!ownerIsSuccess) {
          setToast({
            open: true,
            message: error,
          });

          return;
        }

        setOwners(ownersData);
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
      push("/pets/list");
    }
  }, [state]);

  return id && !pet ? (
    <>Loading...</>
  ) : (
    <Card header={pet ? `Edit Pet: ${pet.name}` : "New Pet"}>
      <form className="max-w-md" onSubmit={handleSubmit}>
        <div className="flex z-0 w-full mb-5 group items-center gap-5">
          <div className="block mb-2 font-medium text-gray-900 dark:text-white w-[4rem]">Name</div>
          <Input
            type="text"
            name="name"
            required
            classNames="w-full"
            defaultValue={state?.data?.name || pet?.name}
            errors={state?.errors?.name}
          />
        </div>
        <div className="flex z-0 w-full mb-5 group items-center gap-5">
          <div className="block mb-2 font-medium text-gray-900 dark:text-white w-[4rem]">Age</div>
          <Input
            type="number"
            name="age"
            classNames="w-full"
            defaultValue={state?.data?.age || pet?.age || ""}
            errors={state?.errors?.age}
          />
        </div>
        <div className="flex z-0 w-full mb-5 group items-center gap-5">
          <div className="block mb-2 font-medium text-gray-900 dark:text-white w-[4rem]">Weight</div>
          <Input
            step={0.1}
            type="number"
            name="weight"
            classNames="w-full"
            defaultValue={state?.data?.weight || pet?.weight || ""}
            errors={state?.errors?.weight}
          />
        </div>
        <div className="flex z-0 w-full mb-5 group items-center gap-5">
          <div className="block mb-2 font-medium text-gray-900 dark:text-white w-[4rem]">Type</div>
          <Input
            type="text"
            name="type"
            required
            classNames="w-full"
            defaultValue={state?.data?.type || pet?.type}
            errors={state?.errors?.type}
          />
        </div>
        <div className="flex z-0 w-full mb-5 group items-center gap-5">
          <div className="block mb-2 font-medium text-gray-900 dark:text-white w-[4rem]">Medical Note</div>
          <Input
            type="text"
            name="medicalNote"
            required
            classNames="w-full"
            defaultValue={state?.data?.medicalNote || pet?.medicalNote}
            errors={state?.errors?.medicalNote}
          />
        </div>
        <div className="flex z-0 w-full mb-5 group items-center gap-5">
          <div className="block mb-2 font-medium text-gray-900 dark:text-white w-[4rem]">Owner</div>
          <div className="w-full">
            <Dropdown options={ownerOptions} selectedOption={selectedOwner} onSelect={(option) => setSelectedOwner(option)} />
          </div>
        </div>
        <div className="flex gap-5 grow justify-end">
          <Button type="button" outline onClick={() => push("/pets/list")}>
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

const WithToastPetDetailPage = withToast(PetDetailPage);

export default WithToastPetDetailPage;
