"use server";

import { Pet } from "@/libs/model";
import fetcher from "@/libs/utils/axios";
import { cookies } from "next/headers";

export const getPets = async (): Promise<Pet[]> => {
  const requestCookies = await cookies();

  try {
    const owners: Pet[] = await fetcher<Pet[]>("/pets", {
      headers: {
        Cookie: requestCookies.toString(),
      },
    });

    return owners;
  } catch (e) {
    return [];
  }
};
