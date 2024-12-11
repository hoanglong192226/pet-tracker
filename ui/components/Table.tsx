import React, { useMemo } from "react";
import { twMerge } from "tailwind-merge";

const ACTIONS_KEY = "actions";

export interface TableHeaderProps {
  id: string;
  name: string;
}

export interface TableRowProps {
  id: string;
  data: { [key: string]: any };
  actions?: React.ReactNode[];
}

interface TableProps {
  headers: TableHeaderProps[];
  rows?: TableRowProps[];
}

const Table = ({ headers, rows }: TableProps) => {
  const tableHeaders: TableHeaderProps[] = useMemo(() => {
    const hasAction = rows?.find((s) => s.actions?.length);

    return [
      ...headers,
      hasAction
        ? {
            id: ACTIONS_KEY,
            name: "",
          }
        : undefined,
    ].flatMap((s) => (s ? s : []));
  }, [headers, rows]);

  return (
    <div className="relative overflow-x-auto">
      <table className="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
        <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
          <tr>
            {tableHeaders.map((header) => (
              <th key={header.id} className="px-6 py-3">
                {header.name}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {rows?.map((row) => (
            <tr key={row.id} className="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
              {tableHeaders.map((header) => (
                <td key={header.id} className={twMerge("px-6 py-4")}>
                  {header.id !== ACTIONS_KEY ? (
                    row.data[header.id]
                  ) : (
                    <div className="flex gap-2 justify-end">{row.actions?.map((s) => s)}</div>
                  )}
                </td>
              ))}
            </tr>
          ))}

          {/* <tr className="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
            <th scope="row" className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
              Apple MacBook Pro 17
            </th>
            <td className="px-6 py-4">Silver</td>
            <td className="px-6 py-4">Laptop</td>
            <td className="px-6 py-4">$2999</td>
          </tr>
          <tr className="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
            <th scope="row" className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
              Microsoft Surface Pro
            </th>
            <td className="px-6 py-4">White</td>
            <td className="px-6 py-4">Laptop PC</td>
            <td className="px-6 py-4">$1999</td>
          </tr>
          <tr className="bg-white dark:bg-gray-800">
            <th scope="row" className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
              Magic Mouse 2
            </th>
            <td className="px-6 py-4">Black</td>
            <td className="px-6 py-4">Accessories</td>
            <td className="px-6 py-4">$99</td>
          </tr> */}
        </tbody>
      </table>
    </div>
  );
};

export default Table;
