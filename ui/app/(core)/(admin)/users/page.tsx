"use client";

import Button from "@/components/Button";
import Card from "@/components/Card";
import Dropdown, { DropdownOption } from "@/components/Dropdown";
import withToast from "@/components/HOCs/withToast";
import Input from "@/components/Input";
import { ToastProps } from "@/components/Toast";
import { getUser, submitUser } from "@/libs/action/user";
import { User, USER_ROLE } from "@/libs/model";
import { useRouter, useSearchParams } from "next/navigation";
import { startTransition, useActionState, useEffect, useMemo, useState } from "react";

const UserDetailPage = ({ setToast }: { setToast: (config: ToastProps) => void }) => {
  const { push, replace } = useRouter();

  const [user, setUSer] = useState<User>();

  const searchParams = useSearchParams();
  const id = searchParams.get("id");

  const [state, formAction, isPending] = useActionState(submitUser, undefined);

  const [selectedRole, setSelectedRole] = useState<DropdownOption>();

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const formData = new FormData(e.currentTarget);
    if (id && !Number.isNaN(id)) {
      formData.append("id", id);
      formData.append("username", user?.username || "");
    }

    formData.set("role", selectedRole?.id || "");

    startTransition(() => {
      formAction(formData);
    });
  };

  const userRoleOptions: DropdownOption[] = useMemo(() => {
    return Object.entries(USER_ROLE).map((entry) => ({
      id: entry[1],
      value: entry[0],
    }));
  }, []);

  useEffect(() => {
    const option = userRoleOptions.find((s) => s.id === user?.role) || userRoleOptions[0];
    setSelectedRole(option);
  }, [userRoleOptions, user]);

  useEffect(() => {
    (async () => {
      if (id) {
        const { isSuccess, data } = await getUser(id);
        if (!isSuccess) {
          replace("/users");

          return;
        }
        if (data?.username === "admin") {
          replace("/users");

          return;
        }
        setUSer(data);
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
      push("/users/list");
    }
  }, [state]);

  return id && !user ? (
    <>Loading...</>
  ) : (
    <Card header={user ? `Edit User: ${user.username}` : "New User"}>
      <form className="max-w-md" onSubmit={handleSubmit}>
        <div className="flex z-0 w-full mb-5 group items-center gap-5">
          <div className="block mb-2 font-medium text-gray-900 w-[6rem]">Username</div>
          <Input
            disable={!!user}
            type="text"
            name="username"
            required
            classNames="w-full"
            defaultValue={state?.data?.username || user?.username}
            errors={state?.errors?.username}
          />
        </div>
        <div className="flex z-0 w-full mb-5 group items-center gap-5">
          <div className="block mb-2 font-medium text-gray-900 w-[6rem]">Password</div>
          <Input
            type="password"
            name="password"
            classNames="w-full"
            required={id ? false : true}
            defaultValue={state?.data?.password}
            errors={state?.errors?.password}
          />
        </div>
        <div className="flex z-0 w-full mb-5 group items-center gap-5">
          <div className="block mb-2 font-medium text-gray-900">Re-password</div>
          <Input
            type="password"
            name="repassword"
            required={id ? false : true}
            classNames="w-full"
            defaultValue={state?.data?.repassword}
            errors={state?.errors?.repassword}
          />
        </div>
        <div className="flex z-0 w-full mb-5 group items-center gap-5">
          <div className="block mb-2 font-medium text-gray-900 w-[8rem]">Role</div>
          <div className="w-full">
            <Dropdown options={userRoleOptions} selectedOption={selectedRole} onSelect={(option) => setSelectedRole(option)} />
          </div>
        </div>
        <div className="flex gap-5 grow justify-end">
          <Button type="button" outline onClick={() => push("/users/list")}>
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

const UserDetailPageWithToast = withToast(UserDetailPage);

export default UserDetailPageWithToast;
