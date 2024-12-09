import { twMerge } from "tailwind-merge";

interface InputProps {
  type: string;
  name: string;
  classNames?: string;
  defaultValue?: any;
  errors?: string[];
  required?: boolean;
  value?: any;
}

const Input = ({ type, name, classNames, defaultValue, errors, required, value }: InputProps) => {
  return (
    <div className="flex flex-col">
      <input
        type={type}
        name={name}
        className={twMerge(
          "bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500",
          classNames,
        )}
        required={required}
        defaultValue={defaultValue}
        value={value}
      />
      <div className="text-xs text-red-500">
        {!!errors && (
          <ul>
            {errors.map((error) => (
              <li key={error}>- {error}</li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
};

export default Input;
