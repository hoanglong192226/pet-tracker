"use client";

import { UserContext } from "app/contexts/user-context";
import { useContext, useEffect, useRef, useState } from "react";
import { twMerge } from "tailwind-merge";

const User = () => {
  const [openUserMenu, setOpenUserMenu] = useState(false);

  const { user } = useContext(UserContext);

  const ref = useRef<HTMLDivElement>(null);

  const handleClickOutside = (event: any) => {
    if (ref.current && !ref.current.contains(event.target)) {
      setOpenUserMenu(false);
    }
  };

  useEffect(() => {
    document.addEventListener("mousedown", handleClickOutside);

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [ref]);

  return (
    <div className="relative" ref={ref}>
      <div
        className="relative w-8 h-8 overflow-hidden bg-gray-100 rounded-full dark:bg-gray-600 cursor-pointer border"
        onClick={() => setOpenUserMenu(!openUserMenu)}
      >
        <svg
          data-dropdown-toggle="dropdownInformation"
          className="absolute w-10 h-10 text-gray-400 -left-1"
          fill="currentColor"
          viewBox="0 0 20 20"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path fillRule="evenodd" d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z" clipRule="evenodd"></path>
        </svg>
      </div>

      <div
        className={twMerge(
          "right-2 top-12 md:right-2 md:top-8 z-10 bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700 dark:divide-gray-600",
          openUserMenu ? "absolute" : "hidden",
        )}
      >
        <div className="px-4 py-3 text-sm text-gray-900 dark:text-white">
          <div className="font-medium truncate">
            {user?.username && user.username[0].toUpperCase() + user.username.slice(1, user.username.length)}
          </div>
        </div>

        <ul className="py-2 text-sm text-gray-700 dark:text-gray-200" aria-labelledby="dropdownInformationButton">
          <li>
            <a href="#" className="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">
              Settings
            </a>
          </li>
        </ul>
        <div className="py-2">
          <a
            href="#"
            className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white"
          >
            Sign out
          </a>
        </div>
      </div>
    </div>
    // <Dropdown placement="bottom-start">
    //   <DropdownTrigger>
    //     <Image width={24} height={24} src={UserIcon} alt="user_logo" />
    //   </DropdownTrigger>
    //   <DropdownMenu aria-label="Profile Actions" variant="flat">
    //     <DropdownSection showDivider aria-label="Profile & Actions">
    //       <DropdownItem key="profile" classNameName="h-14 gap-2 cursor-default" isReadOnly textValue="zoey@example.com">
    //         <LibUser
    //           avatarProps={{
    //             src: "",
    //             classNameName: "hidden",
    //           }}
    //           description="zoey@example.com"
    //           name="Zoe"
    //         />
    //       </DropdownItem>
    //     </DropdownSection>

    //     <DropdownItem key="settings">Settings</DropdownItem>
    //     <DropdownItem key="logout" color="danger">
    //       Log Out
    //     </DropdownItem>
    //   </DropdownMenu>
    // </Dropdown>
  );
};

export default User;