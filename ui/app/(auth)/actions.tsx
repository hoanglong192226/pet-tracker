"use server";

import { FormState, LoginFormState } from "@/libs/schema";
import { LoginPostRequest, LoginPostRequestSchema } from "@/libs/schema/zod-schema";
import fetcher from "@/libs/utils/axios";
import { cookies } from "next/headers";

export const login = async (state: FormState<LoginFormState, LoginPostRequestSchema>, formData: FormData) => {
  const data = Object.fromEntries(formData);
  const validatedFields = LoginPostRequest.safeParse(data);

  if (!validatedFields.success) {
    return {
      isSuccess: false,
      data: data as LoginPostRequestSchema,
      errors: validatedFields.error?.flatten().fieldErrors,
    };
  }

  try {
    const token = await fetcher<string>("/auth/login", {
      data: validatedFields.data,
      method: "POST",
    });

    const cookiesRequest = await cookies();
    cookiesRequest.set({
      name: "token",
      value: token,
      httpOnly: true,
      sameSite: "strict",
    });

    return { isSuccess: true };
  } catch (e: any) {
    return {
      data: data as LoginPostRequestSchema,
      message: e.message as string,
    };
  }
};
