import Footer from "app/(core)/footer";
import MainMenu from "./main-menu";

const CoreLayout = ({ children }: { children: React.ReactNode }) => {
  return (
    <div className="flex flex-col bg-[#F6F9FB] h-screen">
      <MainMenu />
      {children}
      <Footer />
    </div>
  );
};

export default CoreLayout;
