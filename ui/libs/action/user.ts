"use server";

import { ServerResponse, User, UserProfile } from "@/libs/model";
import { USER_PROFILE_COOKIE } from "@/libs/utils";
import fetcher from "@/libs/utils/axios";
import CookiesUtil from "@/libs/utils/cookies";
import { cookies } from "next/headers";

export const getUser = async (): Promise<UserProfile | undefined> => {
  const userProfileCookies = CookiesUtil.COOKIES_PREFIX + USER_PROFILE_COOKIE;
  const cookieRequest = await cookies();

  const cookie = cookieRequest.get(userProfileCookies)?.value;

  if (!cookie) {
    return undefined;
  }

  try {
    const userProfile = await CookiesUtil.unsignCookie(cookie);

    return JSON.parse(userProfile);
  } catch (e) {
    console.error(e);

    return undefined;
  }
};

export const getUsers = async (): Promise<ServerResponse<User[]>> => {
  try {
    const users: User[] = await fetcher<User[]>("/admin/users");

    return {
      data: users,
      isSuccess: true,
    };
  } catch (e: any) {
    return {
      isSuccess: false,
      error: e.message,
    };
  }
};

export const deleteUser = async (id: string) => {
  try {
    const data = await fetcher<User>(`/admin/users/${encodeURI(id)}`, {
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
