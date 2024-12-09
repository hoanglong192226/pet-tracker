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

    return NextResponse.redirect(new URL("/login", request.url));
  }

  if (isLoginUrl(request)) {
    return NextResponse.redirect(new URL("/", request.url));
  }

  const requestCookies = await cookies();

  try {
    const userProfileCookies = CookiesUtil.COOKIES_PREFIX + USER_PROFILE_COOKIE;
    const cookie = request.cookies.get(userProfileCookies)?.value;

    if (!cookie) {
      const userProfile: UserProfle | undefined = await fetcher<UserProfle>("/auth/profile", {
        headers: {
          Cookie: requestCookies.toString(),
        },
      });
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

    return NextResponse.json("UNAUTHENTICATED", { status: 401 });
  }
}
export const config = {
  matcher: ["/((?!api|_next/static|_next/image|favicon.ico).*)"],
};
