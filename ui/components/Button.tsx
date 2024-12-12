import { twMerge } from "tailwind-merge";

interface ButtonProps {
  type?: "button" | "submit" | "reset" | undefined;
  children: React.ReactNode;
  disabled?: boolean;
  classNames?: string;
  loading?: boolean;
  outline?: boolean;
  onClick?: (props: any) => void;
}

const Button = ({ type = "button", children, disabled, classNames, loading, outline, onClick }: ButtonProps) => {
  return (
    <button
      type={type}
      disabled={disabled}
      className={twMerge(
        "bg-gradient-to-r focus:outline-none  shadow-lg dark:shadow-lg font-medium rounded-lg text-sm px-5 py-2.5 text-center",
        !outline
          ? "text-white hover:bg-gradient-to-br from-blue-500 via-blue-600 to-blue-700 shadow-blue-500/50 dark:shadow-blue-800/80"
          : "text-blue-700 hover:text-white border border-blue-700 hover:bg-blue-800 dark:border-blue-500 dark:text-blue-500 dark:hover:text-white dark:hover:bg-blue-500 dark:focus:ring-blue-800",
        disabled && "cursor-not-allowed opacity-25",
        classNames,
      )}
      onClick={onClick}
    >
      <div className="flex ">
        {loading && (
          <svg className="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
            <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
            <path
              className="opacity-75"
              fill="currentColor"
              d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
            ></path>
          </svg>
        )}
        {children}
      </div>
    </button>
  );
};

export default Button;
