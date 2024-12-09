"use client";

import { FormState } from "@/libs/schema";
import { LoginPostRequest, LoginPostRequestSchema } from "@/libs/schema/zod-schema";
import fetcher from "@/libs/utils/axios";

export const login = async (state: FormState, formData: FormData) => {
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
    await fetcher<string>("/auth/login", {
      data: validatedFields.data,
      method: "POST",
    });

    return { isSuccess: true };
  } catch (e: any) {
    return {
      data: data as LoginPostRequestSchema,
      message: e.errorMessage as string,
    };
  }
};
