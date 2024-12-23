"use server";

import { ServerResponse, User, UserProfile } from "@/libs/model";
import { FormState, SubmitOwnerFormState, SubmitUserFormState } from "@/libs/schema";
import { SubmitUserPostRequest, SubmitUserPostRequestSchema } from "@/libs/schema/zod-schema";
import { USER_PROFILE_COOKIE } from "@/libs/utils";
import fetcher from "@/libs/utils/axios";
import CookiesUtil from "@/libs/utils/cookies";
import { cookies } from "next/headers";

export const getUserProfile = async (): Promise<UserProfile | undefined> => {
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

export const getUser = async (id: string): Promise<ServerResponse<User>> => {
  try {
    const user: User = await fetcher<User>(`/admin/users/${encodeURI(id)}`);

    return {
      isSuccess: true,
      data: user,
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

export const submitUser = async (state: FormState<SubmitUserFormState, SubmitUserPostRequestSchema>, formData: FormData) => {
  const data = Object.fromEntries(formData);
  const validatedFields = SubmitUserPostRequest.safeParse({
    ...data,
    username: formData.get("id") ? formData.get("username") : data.username,
  });

  if (!validatedFields.success) {
    return {
      isSuccess: false,
      data: data as any as SubmitUserPostRequestSchema,
      errors: validatedFields.error?.flatten().fieldErrors,
    };
  }

  try {
    await fetcher<string>("/admin/users", {
      data: {
        id: validatedFields.data.id,
        username: validatedFields.data.username,
        password: validatedFields.data.password,
        role: formData.get("role"),
      },
      method: "POST",
    });

    return { isSuccess: true };
  } catch (e: any) {
    console.log(e);

    return {
      data: data as any as SubmitUserPostRequestSchema,
      message: e.message as string,
    };
  }
};
