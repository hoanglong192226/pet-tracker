import { z } from "zod";

export const LoginPostRequest = z.object({
  username: z.string().trim().min(5).max(32),
  password: z.string().trim().min(8).max(64),
});

export type LoginPostRequestSchema = z.infer<typeof LoginPostRequest>;

export const SubmitOwnerPostRequest = z.object({
  id: z.coerce.number().int().optional(),
  name: z.string().trim().min(2).max(20),
  phone: z.string().trim().min(9).max(20),
});

export type SubmitOwnerPostRequestSchema = z.infer<typeof SubmitOwnerPostRequest>;

export const SubmitPetPostRequest = z.object({
  id: z.coerce.number().int().optional(),
  name: z.string().trim().min(2).max(20),
  age: z.coerce.number().int().gt(0).optional(),
  weight: z.coerce.number().gt(0).optional(),
  type: z.string().trim(),
  medicalNote: z.string().trim().optional(),
  ownerId: z.coerce.number().int().optional(),
});

export type SubmitPetPostRequestSchema = z.infer<typeof SubmitPetPostRequest>;

export const SubmitUserPostRequest = z
  .object({
    id: z.coerce.number().int().optional(),
    username: z.string().trim().min(5).max(32).optional(),
    password: z
      .string()
      .trim()
      .transform((val) => (!val.length ? undefined : val))
      .optional()
      .refine((val) => val === undefined || (val.length >= 8 && val.length <= 64), {
        message: "Password must be between 8 and 64 characters if provided.",
      }),
    repassword: z
      .string()
      .trim()
      .transform((val) => (!val.length ? undefined : val))
      .optional()
      .refine((val) => val === undefined || (val.length >= 8 && val.length <= 64), {
        message: "Re-Password must be between 8 and 64 characters if provided.",
      }),
  })
  .refine(
    (s) => {
      if (s.password) {
        return s.password === s.repassword;
      }

      return true;
    },
    { message: "Re-password not match", path: ["repassword"] },
  );

export type SubmitUserPostRequestSchema = z.infer<typeof SubmitUserPostRequest>;
