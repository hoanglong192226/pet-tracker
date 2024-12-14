import { useEffect, useRef, useState } from "react";
import { twMerge } from "tailwind-merge";

export interface DropdownOption {
  id: string;
  value: any;
}

interface DropdownProps {
  options: DropdownOption[];
  selectedOption?: DropdownOption;
  defaultTitle?: string;
  onSelect: (option: DropdownOption) => void;
}

const Dropdown = ({ options, selectedOption, defaultTitle, onSelect }: DropdownProps) => {
  const [open, setOpen] = useState(false);
  const ref = useRef<HTMLDivElement>(null);

  const handleSelect = (option: DropdownOption) => {
    onSelect(option);
    setOpen(false);
  };

  const handleClickOutside = (event: any) => {
    if (ref.current && !ref.current.contains(event.target)) {
      setOpen(false);
    }
  };

  useEffect(() => {
    document.addEventListener("mousedown", handleClickOutside);

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [ref]);

  return (
    <div className="relative" ref={ref}>
      <button
        onClick={() => setOpen(!open)}
        className="flex w-full justify-between text-white bg-blue-700 hover:bg-blue-800 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2 text-center items-center"
        type="button"
      >
        {selectedOption?.value || defaultTitle || "Choose"}
        <svg className="w-2.5 h-2.5 ms-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
          <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
        </svg>
      </button>

      <div
        className={twMerge(
          "absolute z-10 bg-white divide-y divide-gray-100 rounded-lg shadow w-full text-right px-2 top-10 ",
          !open && "hidden",
        )}
      >
        <ul className="py-2 text-sm text-gray-700">
          {options.map((s) => (
            <li key={s.id}>
              <div
                onClick={() => handleSelect(s)}
                className={twMerge("block px-4 py-2 hover:bg-gray-100 cursor-pointer", s.id === selectedOption?.id && "bg-gray-100")}
              >
                {s.value}
              </div>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default Dropdown;
