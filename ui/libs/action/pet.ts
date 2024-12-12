"use server";

import { Pet, ServerResponse } from "@/libs/model";
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
