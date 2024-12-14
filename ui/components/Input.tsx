import { twMerge } from "tailwind-merge";

interface InputProps {
  type: string;
  name: string;
  classNames?: string;
  defaultValue?: any;
  errors?: string[];
  required?: boolean;
  value?: any;
  step?: number;
}

const Input = ({ type, name, classNames, defaultValue, errors, required, value, step }: InputProps) => {
  return (
    <div className="flex flex-col grow">
      <input
        step={step}
        type={type}
        name={name}
        className={twMerge(
          "bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5",
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
