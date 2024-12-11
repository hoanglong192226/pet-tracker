import { z } from "zod";

export const LoginPostRequest = z.object({
  username: z.string().trim().min(5).max(32),
  password: z.string().trim().min(8).max(64),
});

export type LoginPostRequestSchema = z.infer<typeof LoginPostRequest>;

export const SubmitOwnerPostRequest = z.object({
  name: z.string().trim().min(2).max(20),
  phone: z.string().trim().min(9).max(20),
});

export type SubmitOwnerPostRequestSchema = z.infer<typeof SubmitOwnerPostRequest>;
