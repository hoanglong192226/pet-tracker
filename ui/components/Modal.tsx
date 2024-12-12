import Button from "@/components/Button";
import { useState } from "react";
import { twMerge } from "tailwind-merge";

interface ModalProps {
  title: string;
  body: string;
  open?: boolean;
  onClose: () => void;
  onConfirm: () => Promise<void>;
}

const Modal = ({ title, body, open, onClose, onConfirm }: ModalProps) => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string>();

  const handleClose = () => {
    setLoading(false);
    setError("");
    onClose();
  };

  const handleConfirm = async () => {
    try {
      setLoading(true);
      await onConfirm();
      onClose();
    } catch (e: any) {
      console.log(e);
      const error = JSON.parse(e.message);
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div
      className={twMerge(
        "fixed inset-0 z-50 flex justify-center items-center w-full h-full bg-black bg-opacity-50 backdrop-blur-sm",
        !open && "hidden",
      )}
    >
      <div className="relative p-4 w-full max-w-2xl max-h-full">
        <div className="relative bg-white rounded-lg shadow dark:bg-gray-700">
          <div className="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
            <h3 className="text-xl font-semibold text-gray-900 dark:text-white">{title}</h3>
            <button
              onClick={handleClose}
              type="button"
              className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white"
              data-modal-hide="default-modal"
            >
              <svg className="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                <path
                  stroke="currentColor"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="2"
                  d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"
                />
              </svg>
              <span className="sr-only">Close modal</span>
            </button>
          </div>
          <div className="p-4 md:p-5 space-y-4">
            <p className="text-base leading-relaxed text-gray-500 dark:text-gray-400">{body}</p>
          </div>

          <div className="flex items-center gap-5 justify-between p-4 md:p-5 border-t border-gray-200 rounded-b dark:border-gray-600">
            <div className="text-sm text-red-600 dark:text-red-500">{error}</div>
            <div className="flex gap-5 justify-end">
              <Button outline onClick={handleClose}>
                Cancel
              </Button>
              <Button loading={loading} onClick={handleConfirm}>
                Confirm
              </Button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Modal;
