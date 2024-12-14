import React from "react";
import { twMerge } from "tailwind-merge";

interface CardProps {
  children: React.ReactNode;
  header?: string;
  classNames?: string;
  loading?: boolean;
}

const Card = ({ header, children, classNames, loading }: CardProps) => {
  return (
    <div className={twMerge("block w-full p-6 bg-white border border-gray-200 shadow", classNames)}>
      {header && <h5 className="mb-4 text-2xl font-bold tracking-tight text-gray-900">{header}</h5>}
      {loading ? <>Loading...</> : <>{children}</>}
    </div>
  );
};

export default Card;
