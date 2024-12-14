"use server";

import { Pet, ServerResponse } from "@/libs/model";
import { FormState, SubmitPetFormState } from "@/libs/schema";
import { SubmitOwnerPostRequestSchema, SubmitPetPostRequest, SubmitPetPostRequestSchema } from "@/libs/schema/zod-schema";
import fetcher from "@/libs/utils/axios";
import { cookies } from "next/headers";

export const getPets = async (): Promise<ServerResponse<Pet[]>> => {
  const requestCookies = await cookies();

  try {
    const pets: Pet[] = await fetcher<Pet[]>("/pets", {
      headers: {
        Cookie: requestCookies.toString(),
      },
    });

    return {
      isSuccess: true,
      data: pets,
    };
  } catch (e: any) {
    return {
      isSuccess: false,
      error: e.message,
    };
  }
};

export const getPet = async (id: string): Promise<ServerResponse<Pet>> => {
  const requestCookies = await cookies();

  try {
    const pet: Pet = await fetcher<Pet>(`/pets/${encodeURI(id)}`, {
      headers: {
        Cookie: requestCookies.toString(),
      },
    });

    return {
      isSuccess: true,
      data: pet,
    };
  } catch (e: any) {
    return {
      isSuccess: false,
      error: e.message,
    };
  }
};

export const submitPet = async (state: FormState<SubmitPetFormState, SubmitPetPostRequestSchema>, formData: FormData) => {
  const data = Object.fromEntries(formData);
  const validatedFields = SubmitPetPostRequest.safeParse(data);

  if (!validatedFields.success) {
    return {
      isSuccess: false,
      data: data as any as SubmitPetPostRequestSchema,
      errors: validatedFields.error?.flatten().fieldErrors,
    };
  }

  const requestCookies = await cookies();

  console.log(data);
  try {
    await fetcher<string>("/pets", {
      data: { pets: [validatedFields.data] },
      method: "POST",
      headers: {
        Cookie: requestCookies.toString(),
      },
    });

    return { isSuccess: true };
  } catch (e: any) {
    return {
      data: data as any as SubmitPetPostRequestSchema,
      message: e.message as string,
    };
  }
};
