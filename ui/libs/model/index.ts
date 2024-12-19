export interface UserProfile {
  username: string;
  role: string;
  expiredAt: number;
}

export interface User {
  id: number;
  username: string;
  role: string;
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
  owner?: Owner;
}

export interface ServerResponse<T> {
  isSuccess: boolean;
  data?: T;
  error?: string;
}

export enum USER_ROLE {
  ADMIN = "admin",
  MEMBER = "member",
}

export interface AppRoute {
  id: string;
  name: string;
  path: string;
  role: USER_ROLE[];
  display?: boolean;
}
