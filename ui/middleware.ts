import { UserProfle } from "@/libs/model";
import { USER_PROFILE_COOKIE } from "@/libs/utils";
import fetcher from "@/libs/utils/axios";
import CookiesUtil from "@/libs/utils/cookies";
import { cookies } from "next/headers";
import { NextRequest, NextResponse } from "next/server";

const isLoginUrl = (request: NextRequest) => {
  return request.nextUrl.pathname.startsWith("/login", 0);
};

export async function middleware(request: NextRequest) {
  if (!request.cookies.get("token")) {
    if (isLoginUrl(request)) {
      return NextResponse.next();
    }

    (await cookies()).set({
      name: CookiesUtil.COOKIES_PREFIX + "reload",
      value: "true",
    });

    return NextResponse.redirect(new URL("/login", request.url));
  }

  if (isLoginUrl(request)) {
    return NextResponse.redirect(new URL("/", request.url));
  }

  const requestCookies = await cookies();

  const userProfileCookies = CookiesUtil.COOKIES_PREFIX + USER_PROFILE_COOKIE;
  try {
    const cookie = request.cookies.get(userProfileCookies)?.value;

    if (!cookie) {
      const userProfile: UserProfle | undefined = await fetcher<UserProfle>("/auth/profile");
      const signedUserProfileCookies = await CookiesUtil.signCookie(userProfile);

      requestCookies.set({
        name: userProfileCookies,
        value: signedUserProfileCookies,
        sameSite: "strict",
        httpOnly: true,
      });
    } else {
      await CookiesUtil.unsignCookie(cookie);
    }

    return NextResponse.next();
  } catch (e) {
    console.error(e);
    requestCookies.delete("token");
    requestCookies.delete(userProfileCookies);

    return NextResponse.redirect(new URL("/login", request.url));
  }
}
export const config = {
  matcher: ["/((?!api|_next/static|_next/image|favicon.ico).*)"],
};
