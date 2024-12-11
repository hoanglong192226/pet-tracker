"use server";

import { Owner } from "@/libs/model";
import fetcher from "@/libs/utils/axios";
import { cookies } from "next/headers";

export const getOwners = async (): Promise<Owner[]> => {
  const requestCookies = await cookies();

  try {
    const owners: Owner[] = await fetcher<Owner[]>("/owners", {
      headers: {
        Cookie: requestCookies.toString(),
      },
    });

    return owners;
  } catch (e) {
    throw new Error(JSON.stringify(e));
  }
};

export const getOwner = async (id: string): Promise<Owner> => {
  const requestCookies = await cookies();

  try {
    const owner: Owner = await fetcher<Owner>(`/owners/${encodeURI(id)}`, {
      headers: {
        Cookie: requestCookies.toString(),
      },
    });

    return owner;
  } catch (e) {
    throw new Error(JSON.stringify(e));
  }
};
