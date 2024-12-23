import { AppRoute, USER_ROLE } from "@/libs/model";

export const USER_PROFILE_COOKIE = "user_profile";

export const MENU_ROUTE: AppRoute[] = [
  {
    id: "ownerList",
    name: "Owners",
    path: "/owners/list",
    role: [USER_ROLE.ADMIN, USER_ROLE.MEMBER],
    display: true,
  },
  {
    id: "owner",
    name: "Owners",
    path: "/owners",
    role: [USER_ROLE.ADMIN, USER_ROLE.MEMBER],
  },
  {
    id: "petList",
    name: "Pets",
    path: "/pets/list",
    role: [USER_ROLE.ADMIN, USER_ROLE.MEMBER],
    display: true,
  },
  {
    id: "pet",
    name: "Pets",
    path: "/pets",
    role: [USER_ROLE.ADMIN, USER_ROLE.MEMBER],
  },
  {
    id: "user",
    name: "Users",
    path: "/admin/users/list",
    role: [USER_ROLE.ADMIN],
    display: true,
  },
  {
    id: "user",
    name: "Users",
    path: "/admin/users",
    role: [USER_ROLE.ADMIN],
  },
];

export const getRouteFromPathname = (pathname: string): AppRoute | undefined => {
  return MENU_ROUTE.find((s) => s.path === pathname);
};
