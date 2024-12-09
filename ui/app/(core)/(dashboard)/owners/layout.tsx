import Card from "@/components/Card";
import React from "react";

const OwnerLayout = ({ children }: { children: React.ReactNode }) => {
  return <Card header="Owner">{children}</Card>;
};

export default OwnerLayout;
