const DashboardLayout = ({ children }: { children: React.ReactNode }) => {
  return (
    <div className="flex flex-col">
      <div>Menu</div>
      {children}
    </div>
  );
};

export default DashboardLayout;
