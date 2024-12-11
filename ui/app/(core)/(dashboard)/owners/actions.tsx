"use client";

import { FormState, SubmitOwnerFormState } from "@/libs/schema";
import { SubmitOwnerPostRequest, SubmitOwnerPostRequestSchema } from "@/libs/schema/zod-schema";
import fetcher from "@/libs/utils/axios";

export const submitOwner = async (state: FormState<SubmitOwnerFormState, SubmitOwnerPostRequestSchema>, formData: FormData) => {
  const data = Object.fromEntries(formData);
  const validatedFields = SubmitOwnerPostRequest.safeParse(data);

  if (!validatedFields.success) {
    return {
      isSuccess: false,
      data: data as SubmitOwnerPostRequestSchema,
      errors: validatedFields.error?.flatten().fieldErrors,
    };
  }

  try {
    await fetcher<string>("/owners", {
      data: [validatedFields.data],
      method: "POST",
    });

    return { isSuccess: true };
  } catch (e: any) {
    return {
      data: data as SubmitOwnerPostRequestSchema,
      message: e.errorMessage as string,
    };
  }
};
