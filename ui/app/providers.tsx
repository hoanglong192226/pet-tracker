"use client";

import { UserContextProvider } from "app/contexts/user-context";

const Providers = ({ children }: { children: React.ReactNode }) => {
  return <UserContextProvider>{children}</UserContextProvider>;
};

export default Providers;
