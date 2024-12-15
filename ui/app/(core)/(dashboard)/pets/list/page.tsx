"use client";

import AddButton from "@/components/AddButton";
import Button from "@/components/Button";
import Card from "@/components/Card";
import withToast from "@/components/HOCs/withToast";
import Modal from "@/components/Modal";
import Table, { TableHeaderProps, TableRowProps } from "@/components/Table";
import { ToastProps } from "@/components/Toast";
import { getPets, deletePet as deletePetAction } from "@/libs/action/pet";
import { Pet } from "@/libs/model";
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
    id: "age",
    name: "Age",
  },
  {
    id: "weight",
    name: "Weight",
  },
  {
    id: "type",
    name: "Type",
  },
  {
    id: "medicalNote",
    name: "Medical Note",
  },
  {
    id: "ownerName",
    name: "Owner",
  },
];

const PetPage = ({ setToast }: { setToast: (config: ToastProps) => void }) => {
  const [pets, setPets] = useState<Pet[]>();
  const [deletePet, setDeletePet] = useState<Pet>();
  const [openModal, setOpenModal] = useState(false);
  const [container, setContainer] = useState<HTMLElement>();
  const [loading, setLoading] = useState(true);

  const { push } = useRouter();

  const handleEdit = (id: number) => {
    push(`/pets?id=${id}`);
  };

  const handleOpenDeleteModal = (id: number) => {
    const petToDelete = pets?.find((s) => s.id === id);
    setDeletePet(petToDelete);
    setOpenModal(true);
  };

  const handleDelete = async () => {
    if (deletePet) {
      const { isSuccess, error } = await deletePetAction(String(deletePet.id));
      console.log(isSuccess, error);
      if (!isSuccess) {
        setToast({
          open: true,
          message: error,
        });

        return;
      }
      await fetchPets();
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

  const fetchPets = useCallback(async () => {
    setLoading(true);
    const { data, isSuccess, error } = await getPets();
    if (isSuccess) {
      setPets(data);
    } else {
      setToast({
        message: error,
        open: true,
      });
    }
    setLoading(false);
  }, []);

  useEffect(() => {
    fetchPets().finally(() => setLoading(false));
  }, []);

  const ownerRows: TableRowProps[] = useMemo(() => {
    return (pets || []).map((pet) => ({
      id: String(pet.id),
      data: {
        ...pet,
        ownerName: pet.owner?.name ? (
          <Link href={`/owners?id=${pet.owner.id}`} className="text-blue-500 hover:text-blue-800">
            {pet.owner.name}
          </Link>
        ) : (
          <></>
        ),
      },
      actions: [
        <Button key={"edit"} onClick={() => handleEdit(pet.id)}>
          Edit
        </Button>,
        <Button key={"delete"} onClick={() => handleOpenDeleteModal(pet.id)} classNames="from-red-400 via-red-500 to-red-600">
          Delete
        </Button>,
      ],
    }));
  }, [pets]);

  return (
    <Card header="Pet" loading={loading}>
      <Table headers={HEADERS} rows={ownerRows} />
      <Modal
        open={openModal}
        onConfirm={handleDelete}
        onClose={() => setOpenModal(false)}
        title={`Delete ${deletePet?.name}`}
        body={`Are you sure to delete Pet ${deletePet?.name}`}
      />
      {container &&
        createPortal(
          <div className="fixed bottom-10 right-10">
            <AddButton onClick={() => push("/pets")} />
          </div>,
          container,
        )}
    </Card>
  );
};

const PetPageWithToast = withToast(PetPage);

export default PetPageWithToast;
