import { Suspense } from "react";

const DashboardLayout = ({ children }: { children: React.ReactNode }) => {
  return (
    <div className="flex flex-col grow items-center">
      <div className="py-3 max-w-screen-xl w-screen">
        <Suspense fallback={"Loading..."}>{children}</Suspense>
      </div>
    </div>
  );
};

export default DashboardLayout;
