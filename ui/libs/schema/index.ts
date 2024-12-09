export type FormState =
  | {
      isSuccess?: boolean;
      errors?: {
        username?: string[];
        password?: string[];
      };
      message?: string;
    }
  | undefined;
