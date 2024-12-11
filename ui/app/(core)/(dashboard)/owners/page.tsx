"use client";

import Button from "@/components/Button";
import Card from "@/components/Card";
import withToast from "@/components/HOCs/withToast";
import Input from "@/components/Input";
import { ToastProps } from "@/components/Toast";
import { getOwner } from "@/libs/action/owner";
import { Owner } from "@/libs/model";
import { submitOwner } from "app/(core)/(dashboard)/owners/actions";
import { useRouter, useSearchParams } from "next/navigation";
import { useActionState, useEffect, useState } from "react";

const OwnerDetailPage = ({ setToast }: { setToast: (config: ToastProps) => void }) => {
  const [owner, setOwner] = useState<Owner>();
  const [loading, setLoading] = useState(true);
  const [state, formAction, isPending] = useActionState(submitOwner, undefined);
  const { push } = useRouter();

  const searchParams = useSearchParams();
  const id = searchParams.get("id");

  useEffect(() => {
    if (id) {
      (async () => {
        try {
          const ownerData = await getOwner(id);
          setOwner(ownerData);
          setLoading(false);
        } catch (e: any) {
          const error = JSON.parse(e.message);
          setToast({
            message: error.errorMessage,
            open: true,
          });
        } finally {
          setLoading(false);
        }
      })();
    } else {
      setLoading(false);
    }
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

  return loading ? (
    <>Loading...</>
  ) : (
    <Card header={owner ? `Edit Owner: ${owner.name}` : "New Owner"}>
      <form className="max-w-md" action={formAction}>
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
        <Button disabled={isPending} loading={isPending} type="submit">
          Submit
        </Button>
      </form>
    </Card>
  );
};

const WithToastPage = withToast(OwnerDetailPage);

export default WithToastPage;
