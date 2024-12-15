import Toast, { ToastProps } from "@/components/Toast";
import { useEffect, useState } from "react";
import { createPortal } from "react-dom";

const withToast = (Component: any) => {
  const ComponentWithToast = (props: any) => {
    const [container, setContainer] = useState<HTMLElement>();
    const [toastConfig, setToastConfig] = useState<ToastProps>();

    useEffect(() => {
      if (toastConfig?.open) {
        const pageContainer = window.document.getElementById("corePageContainer");
        if (pageContainer) {
          setContainer(pageContainer);
        } else {
          setContainer(window.document.body);
        }
      }
    }, [toastConfig?.open]);

    const setToast = (config: ToastProps) => {
      const onClose = () => {
        setToastConfig(undefined);
        if (config.onClose) {
          config.onClose();
        }
      };

      setToastConfig({ ...config, onClose });
    };

    return (
      <>
        {toastConfig?.onClose && createPortal(<Toast {...toastConfig} />, container || window.document.body)}
        <Component {...props} setToast={setToast} />
      </>
    );
  };

  return ComponentWithToast;
};

export default withToast;
