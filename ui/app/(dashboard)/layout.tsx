import DashboardMenu from "./dashboard-menu";
import { NextUIProvider } from "@nextui-org/react";

const DashboardLayout = ({ children }: { children: React.ReactNode }) => {
  return (
    <NextUIProvider>
      <div className="flex flex-col bg-[#F6F9FB] h-screen">
        <DashboardMenu />
        {children}
      </div>
    </NextUIProvider>
  );
};

export default DashboardLayout;
