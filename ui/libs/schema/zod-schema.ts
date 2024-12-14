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
