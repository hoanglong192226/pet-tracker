"use server";

import { FormState, LoginFormState } from "@/libs/schema";
import { LoginPostRequest, LoginPostRequestSchema } from "@/libs/schema/zod-schema";
import { USER_PROFILE_COOKIE } from "@/libs/utils";
import fetcher from "@/libs/utils/axios";
import CookiesUtil from "@/libs/utils/cookies";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";

export const login = async (state: FormState<LoginFormState, LoginPostRequestSchema>, formData: FormData) => {
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
    const token = await fetcher<string>("/auth/login", {
      data: validatedFields.data,
      method: "POST",
    });

    const cookiesRequest = await cookies();
    cookiesRequest.set({
      name: "token",
      value: token,
      httpOnly: true,
      sameSite: "strict",
    });

    return { isSuccess: true };
  } catch (e: any) {
    return {
      data: data as LoginPostRequestSchema,
      message: e.message as string,
    };
  }
};

export const logout = async () => {
  try {
    await fetcher<string>("/auth/logout", {
      method: "POST",
    });
  } catch (e) {
    console.error(e);
  }
  const cookiesRequest = await cookies();
  cookiesRequest.delete("token");
  cookiesRequest.delete(CookiesUtil.COOKIES_PREFIX + USER_PROFILE_COOKIE);
  redirect("/");
};
