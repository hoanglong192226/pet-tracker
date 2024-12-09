import Image from "next/image";
import Link from "next/link";
import AppLogo from "../../public/logo.svg";
import User from "./user";
// import { Navbar, NavbarBrand, NavbarContent, NavbarItem } from "@nextui-org/react";

const MainMenu = () => {
  return (
    <></>
    // <Navbar className="border-b shadow">
    //   <NavbarBrand>
    //     <Link href={"/"}>
    //       <div className="flex items-center">
    //         <Image src={AppLogo} alt="app_logo" width={32} height={32} />
    //         <div className="font-bold text-inherit ml-2">PET TRACKER</div>
    //       </div>
    //     </Link>
    //   </NavbarBrand>
    //   <NavbarContent className="hidden sm:flex gap-4" justify="center">
    //     <NavbarItem>
    //       <Link color="foreground" href="/owners">
    //         Owners
    //       </Link>
    //     </NavbarItem>
    //     <NavbarItem isActive>
    //       <Link aria-current="page" href="/pets">
    //         Pets
    //       </Link>
    //     </NavbarItem>
    //     <NavbarItem className="ml-10">
    //       <User />
    //     </NavbarItem>
    //   </NavbarContent>
    // </Navbar>
  );
};

export default MainMenu;
