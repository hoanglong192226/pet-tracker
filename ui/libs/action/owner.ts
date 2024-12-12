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
    return [];
  }
};

export const getOwner = async (id: string): Promise<Owner | undefined> => {
  const requestCookies = await cookies();

  try {
    const owner: Owner = await fetcher<Owner>(`/owners/${encodeURI(id)}`, {
      headers: {
        Cookie: requestCookies.toString(),
      },
    });

    return owner;
  } catch (e) {
    return undefined;
  }
};

export const deleteOwner = async (id: string) => {
  const requestCookies = await cookies();

  try {
    await fetcher<Owner>(`/owners/${encodeURI(id)}`, {
      headers: {
        Cookie: requestCookies.toString(),
      },
      method: "DELETE",
    });
  } catch (e) {
    throw new Error(JSON.stringify(e));
  }
};
