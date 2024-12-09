import React from "react";
import { twMerge } from "tailwind-merge";

interface CardProps {
  children: React.ReactNode;
  header?: string;
  classNames?: string;
}

const Card = ({ header, children, classNames }: CardProps) => {
  return (
    <div className={twMerge("block w-full p-6 bg-white border border-gray-200 shadow", classNames)}>
      {header && <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">{header}</h5>}
      {children}
    </div>
  );
};

export default Card;
