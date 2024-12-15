"use server";

import { Owner, Pet, ServerResponse } from "@/libs/model";
import { FormState, SubmitOwnerFormState } from "@/libs/schema";
import { SubmitOwnerPostRequest, SubmitOwnerPostRequestSchema } from "@/libs/schema/zod-schema";
import fetcher from "@/libs/utils/axios";

export const getOwners = async (): Promise<ServerResponse<Owner[]>> => {
  try {
    const owners: Owner[] = await fetcher<Owner[]>("/owners");

    return {
      data: owners,
      isSuccess: true,
    };
  } catch (e: any) {
    return {
      isSuccess: false,
      error: e.message,
    };
  }
};

export const getOwner = async (id: string): Promise<ServerResponse<Owner>> => {
  try {
    const owner: Owner = await fetcher<Owner>(`/owners/${encodeURI(id)}`);

    return {
      isSuccess: true,
      data: owner,
    };
  } catch (e: any) {
    return {
      isSuccess: false,
      error: e.message,
    };
  }
};

export const deleteOwner = async (id: string) => {
  try {
    const data = await fetcher<Owner>(`/owners/${encodeURI(id)}`, {
      method: "DELETE",
    });

    return {
      data,
      isSuccess: true,
    };
  } catch (e: any) {
    return {
      isSuccess: false,
      error: e.message,
    };
  }
};

export const submitOwner = async (state: FormState<SubmitOwnerFormState, SubmitOwnerPostRequestSchema>, formData: FormData) => {
  const data = Object.fromEntries(formData);
  const validatedFields = SubmitOwnerPostRequest.safeParse(data);

  if (!validatedFields.success) {
    return {
      isSuccess: false,
      data: data as any as SubmitOwnerPostRequestSchema,
      errors: validatedFields.error?.flatten().fieldErrors,
    };
  }

  const updatedPets: Pet[] = formData.has("pets") ? JSON.parse(formData.get("pets") as string) : [];

  if (updatedPets.length) {
    try {
      await fetcher<string>("/pets", {
        data: { pets: updatedPets },
        method: "POST",
      });
    } catch (e: any) {
      return {
        data: data as any as SubmitOwnerPostRequestSchema,
        message: e.message as string,
      };
    }
  }

  try {
    await fetcher<string>("/owners", {
      data: { owners: [validatedFields.data] },
      method: "POST",
    });

    return { isSuccess: true };
  } catch (e: any) {
    return {
      data: data as any as SubmitOwnerPostRequestSchema,
      message: e.message as string,
    };
  }
};
