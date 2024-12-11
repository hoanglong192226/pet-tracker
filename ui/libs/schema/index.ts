export interface LoginFormState {
  username: string[];
  password: string[];
}

export interface SubmitOwnerFormState {
  name: string[];
  phone: string[];
}

export type FormState<T, V> =
  | {
      isSuccess?: boolean;
      errors?: Partial<T>;
      message?: string;
      data?: V;
    }
  | undefined;
