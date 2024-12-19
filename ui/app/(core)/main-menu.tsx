"use client";

import Image from "next/image";
import Link from "next/link";
import AppLogo from "../../public/logo.svg";
import User from "./user";
import { useContext, useMemo, useState } from "react";
import { twMerge } from "tailwind-merge";
import { usePathname } from "next/navigation";
import { MENU_ROUTE } from "@/libs/utils";
import { UserContext } from "app/contexts/user-context";
import { USER_ROLE, AppRoute } from "@/libs/model";

const MainMenu = () => {
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const path = usePathname();
  const { user } = useContext(UserContext);
  const allowedRoutes: AppRoute[] = useMemo(() => {
    if (!user) {
      return [];
    }

    return MENU_ROUTE.filter((s) => s.display && s.role.includes(user.role as USER_ROLE));
  }, [user]);

  return (
    <nav className="bg-white border-gray-200 shadow">
      <div className="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
        <Link href="/" className="flex items-center space-x-3 rtl:space-x-reverse">
          <Image src={AppLogo} alt="app_logo" width={32} height={32} />
          <span className="self-center text-2xl font-semibold whitespace-nowrap">Pet Tracker</span>
        </Link>
        <button
          data-collapse-toggle="navbar-default"
          type="button"
          className="inline-flex items-center p-2 w-10 h-10 justify-center text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200"
          aria-controls="navbar-default"
          aria-expanded={sidebarOpen}
          onClick={() => setSidebarOpen(!sidebarOpen)}
        >
          <span className="sr-only">Open main menu</span>
          <svg className="w-5 h-5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 17 14">
            <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M1 1h15M1 7h15M1 13h15" />
          </svg>
        </button>
        <div className={twMerge("w-full md:block md:w-auto", sidebarOpen ? "block" : "hidden")} id="navbar-default">
          <ul
            className={twMerge(
              "md:items-center items-end font-medium flex flex-col gap-3 p-4 md:p-0 mt-4 border border-gray-100 rounded-lg bg-gray-50 md:flex-row md:space-x-8 rtl:space-x-reverse md:mt-0 md:border-0 md:bg-white",
            )}
          >
            {allowedRoutes.map((s) => (
              <li key={s.id}>
                <Link
                  href={s.path}
                  className={twMerge(
                    "block py-2 px-3 rounded  md:p-0",
                    path.startsWith(s.path, 0) && "text-white bg-blue-700 md:bg-transparent md:text-blue-700",
                  )}
                >
                  {s.name}
                </Link>
              </li>
            ))}
            <li>
              <User />
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default MainMenu;
