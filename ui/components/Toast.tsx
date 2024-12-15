import { useEffect, useState } from "react";

export interface ToastProps {
  message?: string;
  open?: boolean;
  onClose?: () => void;
}

const Toast = ({ message, open, onClose }: ToastProps) => {
  const [openToast, setOpenToast] = useState(open);

  const closeToast = () => {
    if (onClose) {
      onClose();
    }
    setOpenToast(false);
  };

  useEffect(() => {
    if (openToast) {
      const timer = setTimeout(() => closeToast(), 5000);

      return () => clearTimeout(timer);
    }
  }, [openToast]);

  return openToast ? (
    <div className="fixed right-0 bottom-4">
      <div className="flex items-center w-full max-w-xs p-4 mb-4 text-gray-500 bg-white rounded-lg shadow min-w-[15rem]" role="alert">
        <div className="inline-flex items-center justify-center flex-shrink-0 w-8 h-8 text-red-500 bg-red-100 rounded-lg">
          <svg className="w-5 h-5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
            <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5Zm3.707 11.793a1 1 0 1 1-1.414 1.414L10 11.414l-2.293 2.293a1 1 0 0 1-1.414-1.414L8.586 10 6.293 7.707a1 1 0 0 1 1.414-1.414L10 8.586l2.293-2.293a1 1 0 0 1 1.414 1.414L11.414 10l2.293 2.293Z" />
          </svg>
          <span className="sr-only">Error icon</span>
        </div>
        <div className="ms-3 text-sm font-normal">{message}</div>
        <button
          onClick={closeToast}
          type="button"
          className="ms-auto -mx-1.5 -my-1.5 bg-white text-gray-400 hover:text-gray-900 rounded-lg p-1.5 hover:bg-gray-100 inline-flex items-center justify-center h-8 w-8"
          data-dismiss-target="#toast-danger"
          aria-label="Close"
        >
          <span className="sr-only">Close</span>
          <svg className="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
            <path
              stroke="currentColor"
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"
            />
          </svg>
        </button>
      </div>
    </div>
  ) : (
    <></>
  );
};

export default Toast;
