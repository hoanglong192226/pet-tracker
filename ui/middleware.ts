import { logout } from "@/libs/action/auth";
import { USER_ROLE, UserProfile } from "@/libs/model";
import { getRouteFromPathname, USER_PROFILE_COOKIE } from "@/libs/utils";
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
      const userProfile: UserProfile | undefined = await fetcher<UserProfile>("/auth/profile");
      const signedUserProfileCookies = await CookiesUtil.signCookie(userProfile);

      requestCookies.set({
        name: userProfileCookies,
        value: signedUserProfileCookies,
        sameSite: "strict",
        httpOnly: true,
      });
    } else {
      const user: UserProfile = JSON.parse(await CookiesUtil.unsignCookie(cookie));
      if (!Object.values(USER_ROLE).includes(user.role as USER_ROLE)) {
        return await logout();
      }

      const route = getRouteFromPathname(request.nextUrl.pathname);
      if (!route?.role.includes(user.role as USER_ROLE)) {
        return NextResponse.redirect(new URL("/", request.url));
      }
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
