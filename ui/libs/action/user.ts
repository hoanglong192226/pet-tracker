"use server";

import { UserProfle } from "@/libs/model";
import { USER_PROFILE_COOKIE } from "@/libs/utils";
import CookiesUtil from "@/libs/utils/cookies";
import { cookies } from "next/headers";

export const getUser = async (): Promise<UserProfle | undefined> => {
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
