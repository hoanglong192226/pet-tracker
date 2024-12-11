import Toast, { ToastProps } from "@/components/Toast";
import { useEffect, useState } from "react";
import { createPortal } from "react-dom";

const withToast = (Component: any) => {
  const ComponentWithToast = (props: any) => {
    const [open, setOpen] = useState(false);
    const [container, setContainer] = useState<HTMLElement>();
    const [toastConfig, setToastConfig] = useState<ToastProps>();

    useEffect(() => {
      if (open) {
        const pageContainer = window.document.getElementById("corePageContainer");
        if (pageContainer) {
          setContainer(pageContainer);
        } else {
          setContainer(window.document.body);
        }
      }
    }, [open]);

    const setToast = (config: ToastProps) => {
      setOpen(true);
      setToastConfig(config);
    };

    return (
      <>
        {toastConfig?.message && createPortal(<Toast open={open} {...toastConfig} />, container || window.document.body)}
        <Component {...props} setToast={setToast} />
      </>
    );
  };

  return ComponentWithToast;
};

export default withToast;
