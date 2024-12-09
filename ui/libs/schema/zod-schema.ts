import { z } from "zod";

export const LoginPostRequest = z.object({
  username: z.string().trim().min(5).max(32),
  password: z.string().trim().min(8).max(64),
});

export type LoginPostRequestSchema = z.infer<typeof LoginPostRequest>;
