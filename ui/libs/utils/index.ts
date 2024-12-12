import { AppRoute } from "@/libs/model";

export const USER_PROFILE_COOKIE = "user_profile";

export const MENU_ROUTE: AppRoute[] = [
  {
    id: "owner",
    name: "Owners",
    path: "/owners/list",
    role: ["ROLE_admin", "ROLE_member"],
  },
  {
    id: "pet",
    name: "Pets",
    path: "/pets/list",
    role: ["ROLE_admin", "ROLE_member"],
  },
  {
    id: "user",
    name: "Users",
    path: "/users/list",
    role: ["ROLE_admin"],
  },
];
