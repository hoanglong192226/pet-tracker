interface AddButtonProps {
  onClick: () => void;
}

const AddButton = ({ onClick }: AddButtonProps) => {
  return (
    <button
      onClick={onClick}
      className="relative inline-flex items-center justify-center p-0.5 mb-2 me-2 overflow-hidden text-4xl font-medium text-gray-900 rounded-full group bg-gradient-to-br from-purple-600 to-blue-500 group-hover:from-purple-600 group-hover:to-blue-500 hover:text-white dark:text-white focus:outline-none focus:ring-blue-300 dark:focus:ring-blue-800 shadow"
    >
      <span className="relative px-4 py-1.5 transition-all ease-in duration-75 bg-white dark:bg-gray-900 rounded-full group-hover:bg-opacity-0">
        +
      </span>
    </button>
  );
};

export default AddButton;
