const DashboardLayout = ({ children }: { children: React.ReactNode }) => {
  return (
    <div className="flex flex-col grow items-center">
      <div className="py-3 max-w-screen-xl w-screen">{children}</div>
    </div>
  );
};

export default DashboardLayout;
