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
      <table className="w-full text-sm text-left rtl:text-right text-gray-500">
        <thead className="text-xs text-gray-700 uppercase bg-gray-50">
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
            <tr key={row.id} className="bg-white border-b">
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
        </tbody>
      </table>
    </div>
  );
};

export default Table;
