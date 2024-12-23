"use client";

import AddButton from "@/components/AddButton";
import Button from "@/components/Button";
import Card from "@/components/Card";
import withToast from "@/components/HOCs/withToast";
import Modal from "@/components/Modal";
import Table, { TableHeaderProps, TableRowProps } from "@/components/Table";
import { ToastProps } from "@/components/Toast";
import { getUsers, deleteUser as deleteUserAction } from "@/libs/action/user";
import { User, USER_ROLE } from "@/libs/model";
import { UserContext } from "app/contexts/user-context";
import { useRouter } from "next/navigation";
import { useCallback, useContext, useEffect, useMemo, useState } from "react";
import { createPortal } from "react-dom";

const HEADERS: TableHeaderProps[] = [
  {
    id: "username",
    name: "Username",
  },
  {
    id: "role",
    name: "Role",
  },
];

const UserPage = ({ setToast }: { setToast: (config: ToastProps) => void }) => {
  const { user: userProfile } = useContext(UserContext);

  const [users, setUsers] = useState<User[]>();
  const [deleteUser, setDeleteUser] = useState<User>();
  const [openModal, setOpenModal] = useState(false);
  const [container, setContainer] = useState<HTMLElement>();
  const [loading, setLoading] = useState(true);

  const { push } = useRouter();

  const handleEdit = (id: number) => {
    push(`/users?id=${id}`);
  };

  const handleOpenDeleteModal = (id: number) => {
    const userToDelete = users?.find((s) => s.id === id);
    setDeleteUser(userToDelete);
    setOpenModal(true);
  };

  const handleDelete = async () => {
    if (deleteUser) {
      const { isSuccess, error } = await deleteUserAction(String(deleteUser.id));
      if (!isSuccess) {
        setToast({
          open: true,
          message: error,
        });

        return;
      }
      await fetchUsers();
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

  const fetchUsers = useCallback(async () => {
    setLoading(true);
    const { data, isSuccess, error } = await getUsers();
    if (isSuccess) {
      setUsers(data);
    } else {
      setToast({
        message: error,
        open: true,
      });
    }
    setLoading(false);
  }, []);

  useEffect(() => {
    fetchUsers().finally(() => {
      setLoading(false);
    });
  }, []);

  const userRows: TableRowProps[] = useMemo(() => {
    return (users || [])
      .filter((s) => s.username !== "admin" && s.username !== userProfile?.username)
      .map((user) => ({
        id: String(user.id),
        data: {
          ...user,
          role: Object.entries(USER_ROLE).find((s) => s[1] === user.role)?.[0],
        },
        actions: [
          <Button key={"edit"} onClick={() => handleEdit(user.id)}>
            Edit
          </Button>,
          <Button key={"delete"} onClick={() => handleOpenDeleteModal(user.id)} classNames="from-red-400 via-red-500 to-red-600">
            Delete
          </Button>,
        ],
      }));
  }, [users]);

  return (
    <Card header="User" loading={loading}>
      <Table headers={HEADERS} rows={userRows} />
      <Modal
        open={openModal}
        onConfirm={handleDelete}
        onClose={() => setOpenModal(false)}
        title={`Delete ${deleteUser?.username}`}
        body={`Are you sure to delete User ${deleteUser?.username}`}
      />
      {container &&
        createPortal(
          <div className="fixed bottom-10 right-10">
            <AddButton onClick={() => push("/users")} />
          </div>,
          container,
        )}
    </Card>
  );
};

const UserPageWithToast = withToast(UserPage);

export default UserPageWithToast;
