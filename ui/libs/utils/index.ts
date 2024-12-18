import { AppRoute } from "@/libs/model";

export const USER_PROFILE_COOKIE = "user_profile";

export const MENU_ROUTE: AppRoute[] = [
  {
    id: "ownerList",
    name: "Owners",
    path: "/owners/list",
    role: ["ROLE_admin", "ROLE_member"],
    display: true,
  },
  {
    id: "owner",
    name: "Owners",
    path: "/owners",
    role: ["ROLE_admin", "ROLE_member"],
  },
  {
    id: "petList",
    name: "Pets",
    path: "/pets/list",
    role: ["ROLE_admin", "ROLE_member"],
    display: true,
  },
  {
    id: "pet",
    name: "Pets",
    path: "/pets",
    role: ["ROLE_admin", "ROLE_member"],
  },
  {
    id: "user",
    name: "Users",
    path: "/users/list",
    role: ["ROLE_admin"],
    display: true,
  },
];

export const getRouteFromPathname = (pathname: string): AppRoute | undefined => {
  return MENU_ROUTE.find((s) => s.path === pathname);
};
