"use client";

import AddButton from "@/components/AddButton";
import Button from "@/components/Button";
import Card from "@/components/Card";
import withToast from "@/components/HOCs/withToast";
import Modal from "@/components/Modal";
import Table, { TableHeaderProps, TableRowProps } from "@/components/Table";
import { ToastProps } from "@/components/Toast";
import { getOwners, deleteOwner as deleteOwnerAction } from "@/libs/action/owner";
import { Owner } from "@/libs/model";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useCallback, useEffect, useMemo, useState } from "react";
import { createPortal } from "react-dom";

const HEADERS: TableHeaderProps[] = [
  {
    id: "name",
    name: "Name",
  },
  {
    id: "phone",
    name: "Phone",
  },
  {
    id: "pets",
    name: "Pets",
  },
];

const OwnerPage = ({ setToast }: { setToast: (config: ToastProps) => void }) => {
  const [owners, setOwners] = useState<Owner[]>();
  const [deleteOwner, setDeleteOwner] = useState<Owner>();
  const [openModal, setOpenModal] = useState(false);
  const [container, setContainer] = useState<HTMLElement>();
  const [loading, setLoading] = useState(true);

  const { push } = useRouter();

  const handleEdit = (id: number) => {
    push(`/owners?id=${id}`);
  };

  const handleOpenDeleteModal = (id: number) => {
    const ownerToDelete = owners?.find((s) => s.id === id);
    setDeleteOwner(ownerToDelete);
    setOpenModal(true);
  };

  const handleDelete = async () => {
    if (deleteOwner) {
      const { isSuccess, error } = await deleteOwnerAction(String(deleteOwner.id));
      if (!isSuccess) {
        setToast({
          open: true,
          message: error,
        });

        return;
      }
      await fetchOwners();
    }
  };

  useEffect(() => {
    if (typeof window === "undefined") {
      return;
    }
    const pageContainer = window.document.getElementById("corePageContainer");
    if (pageContainer) {
      setContainer(pageContainer);
    } else {
      setContainer(window.document.body);
    }
  }, []);

  const fetchOwners = useCallback(async () => {
    setLoading(true);
    const { data, isSuccess, error } = await getOwners();
    if (isSuccess) {
      setOwners(data);
    } else {
      setToast({
        message: error,
        open: true,
      });
    }
    setLoading(false);
  }, []);

  useEffect(() => {
    fetchOwners().finally(() => {
      setLoading(false);
    });
  }, []);

  const ownerRows: TableRowProps[] = useMemo(() => {
    return (owners || []).map((owner) => ({
      id: String(owner.id),
      data: {
        ...owner,
        pets: (
          <div className="flex flex-wrap">
            {owner.pets.map((s) => (
              <Link
                href={`/pets?id=${s.id}`}
                key={s.id}
                className="max-w-max max-h-max relative bg-blue-100 text-blue-500 hover:text-blue-800 text-sm font-medium me-2 px-3 py-0.5 my-1 rounded"
              >
                {s.name}
              </Link>
            ))}
          </div>
        ),
      },
      actions: [
        <Button key={"edit"} onClick={() => handleEdit(owner.id)}>
          Edit
        </Button>,
        <Button key={"delete"} onClick={() => handleOpenDeleteModal(owner.id)} classNames="from-red-400 via-red-500 to-red-600">
          Delete
        </Button>,
      ],
    }));
  }, [owners]);

  return (
    <Card header="Owner" loading={loading}>
      <Table headers={HEADERS} rows={ownerRows} />
      <Modal
        open={openModal}
        onConfirm={handleDelete}
        onClose={() => setOpenModal(false)}
        title={`Delete ${deleteOwner?.name}`}
        body={`Are you sure to delete Owner ${deleteOwner?.name}`}
      />
      {container &&
        createPortal(
          <div className="fixed bottom-10 right-10">
            <AddButton onClick={() => push("/owners")} />
          </div>,
          container,
        )}
    </Card>
  );
};

const OwnerPageWithToast = withToast(OwnerPage);

export default OwnerPageWithToast;
