import MainMenu from "./main-menu";

const CoreLayout = ({ children }: { children: React.ReactNode }) => {
  return (
    <div className="flex flex-col bg-[#F6F9FB] h-screen">
      <MainMenu />
      {children}
    </div>
  );
};

export default CoreLayout;
