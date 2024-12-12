export interface UserProfle {
  username: string;
  role: string;
  expiredAt: number;
}

export interface Owner {
  id: number;
  name: string;
  phone: string;
  pets: Omit<Pet, "owner">[];
}

export interface Pet {
  id: number;
  name: string;
  age: number;
  weight: number;
  type: string;
  medicalNote: string;
  owner: Owner;
}
