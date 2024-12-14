"use client";

import Button from "@/components/Button";
import Input from "@/components/Input";
import { login } from "app/(auth)/actions";
import { useRouter } from "next/navigation";
import { useActionState, useEffect } from "react";
import { useFormStatus } from "react-dom";

const SubmitButton = () => {
  const { pending } = useFormStatus();

  return (
    <Button disabled={pending} type="submit" classNames="text-md">
      Log in
    </Button>
  );
};

const UsernameInput = ({ username, errors }: { username?: string; errors?: string[] }) => {
  return (
    <div className="flex flex-col">
      <div className="flex">
        <span className="inline-flex items-center px-3 text-sm text-gray-900 bg-gray-200 border border-e-0 border-gray-300 rounded-s-md">
          <svg
            className="w-4 h-4 text-gray-500"
            aria-hidden="true"
            xmlns="http://www.w3.org/2000/svg"
            fill="currentColor"
            viewBox="0 0 20 20"
          >
            <path d="M10 0a10 10 0 1 0 10 10A10.011 10.011 0 0 0 10 0Zm0 5a3 3 0 1 1 0 6 3 3 0 0 1 0-6Zm0 13a8.949 8.949 0 0 1-4.951-1.488A3.987 3.987 0 0 1 9 13h2a3.987 3.987 0 0 1 3.951 3.512A8.949 8.949 0 0 1 10 18Z" />
          </svg>
        </span>
        <input
          type="text"
          name="username"
          className="rounded-none rounded-e-lg bg-gray-50 border border-gray-300 text-gray-900 focus:border-blue-500 block flex-1 min-w-0 w-full text-sm p-2.5"
          placeholder="username"
          defaultValue={username}
          required
        />
      </div>
      <div className="text-xs text-red-600">{!!errors && <ul>{errors?.map((error) => <li key={error}>- {error}</li>)}</ul>}</div>
    </div>
  );
};

const Login = () => {
  const { replace } = useRouter();

  const [state, formAction] = useActionState(login, undefined);

  useEffect(() => {
    if (state?.isSuccess) {
      replace("/");
    }
  }, [state?.isSuccess, replace]);

  return (
    <form className="flex grow h-screen items-center justify-center bg-blue-400" action={formAction}>
      <div className="p-16 w-[30rem] bg-white border shadow rounded">
        <div className="flex gap-3 justify-center mb-8">
          <h1 className="text-2xl font-bold uppercase text-blue-500">Sign in</h1>
        </div>
        <div className="flex flex-col gap-6">
          <div className="flex flex-col gap-5">
            <UsernameInput username={state?.data?.username} errors={state?.errors?.username} />
            <Input type="password" name="password" required defaultValue={state?.data?.password} errors={state?.errors?.password} />
          </div>
          {state?.message && (
            <div className="p-4 text-sm text-red-800 rounded-lg bg-red-50" role="alert">
              <span className="font-medium">{state.message}</span>
            </div>
          )}
          <SubmitButton />
        </div>
      </div>
    </form>
  );
};

export default Login;
