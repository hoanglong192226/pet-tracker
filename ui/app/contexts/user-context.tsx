import { getUser } from "@/libs/action/user";
import { UserProfle } from "@/libs/model";
import { usePathname } from "next/navigation";
import { createContext, useEffect, useState } from "react";

interface UserContextProps {
  user?: UserProfle;
  invalidateUser: () => void;
}

interface UserContextProviderProps {
  children: React.ReactNode;
}

export const UserContext = createContext<UserContextProps>({ invalidateUser: () => {} });

export const UserContextProvider = ({ children }: UserContextProviderProps) => {
  const [user, setUser] = useState<UserProfle>();
  const pathName = usePathname();

  const invalidateUser = () => {
    setUser(undefined);
  };

  useEffect(() => {
    if (!user && !pathName.startsWith("/login", 0)) {
      getUser().then((s) => {
        if (!s) {
          setUser(s);
        }
      });
    }
  }, [pathName, user]);

  return <UserContext.Provider value={{ user, invalidateUser }}>{children}</UserContext.Provider>;
};
