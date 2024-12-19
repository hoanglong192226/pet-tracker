import { getUserProfile } from "@/libs/action/user";
import { UserProfile } from "@/libs/model";
import CookiesUtil from "@/libs/utils/cookies";
import { usePathname, useRouter, useSearchParams } from "next/navigation";
import { createContext, useEffect, useState } from "react";

interface UserContextProps {
  user?: UserProfile;
  invalidateUser: () => void;
}

interface UserContextProviderProps {
  children: React.ReactNode;
}

export const UserContext = createContext<UserContextProps>({ invalidateUser: () => {} });

export const UserContextProvider = ({ children }: UserContextProviderProps) => {
  const [user, setUser] = useState<UserProfile>();
  const pathName = usePathname();

  const invalidateUser = () => {
    setUser(undefined);
  };
  const reloadCookie = CookiesUtil.getCookie("reload");

  useEffect(() => {
    if (!user && !pathName.startsWith("/login", 0)) {
      getUserProfile().then((s) => {
        if (s) {
          setUser(s);
        }
      });
    }
  }, [pathName, user]);

  useEffect(() => {
    if (reloadCookie) {
      CookiesUtil.eraseCookie("reload");
      window.location.href = "/login";
    }
  }, [reloadCookie]);

  return <UserContext.Provider value={{ user, invalidateUser }}>{children}</UserContext.Provider>;
};
