"use client";

import Button from "@/components/Button";
import Card from "@/components/Card";
import Table, { TableHeaderProps, TableRowProps } from "@/components/Table";
import { getOwners } from "@/libs/action/owner";
import { Owner } from "@/libs/model";
import { useRouter } from "next/navigation";
import { useCallback, useEffect, useMemo, useState } from "react";

const HEADERS: TableHeaderProps[] = [
  {
    id: "name",
    name: "Name",
  },
  {
    id: "phone",
    name: "Phone",
  },
];

const OwnerPage = () => {
  const [owners, setOwners] = useState<Owner[]>([]);
  const { push } = useRouter();

  const handleEdit = (id: number) => {
    push(`/owners?id=${id}`);
  };

  const fetchOwners = useCallback(async () => {
    const ownersData = await getOwners();
    setOwners(ownersData);
  }, []);

  useEffect(() => {
    fetchOwners();
  }, []);

  const ownerRows: TableRowProps[] = useMemo(() => {
    return owners.map((owner) => ({
      id: String(owner.id),
      data: owner,
      actions: [
        <Button key={"edit"} onClick={() => handleEdit(owner.id)}>
          Edit
        </Button>,
      ],
    }));
  }, [owners]);

  return (
    <Card header="Owner">
      <Table headers={HEADERS} rows={ownerRows} />
    </Card>
  );
};

export default OwnerPage;
