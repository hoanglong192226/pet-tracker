import { logout } from "@/libs/action/auth";
import axios, { AxiosRequestConfig } from "axios";
import { cookies } from "next/headers";

const BASE_URL = process.env.NEXT_PUBLIC_SERVER_HOST + "/api/v1";

const fetcher = async <T>(url: string, config?: AxiosRequestConfig) => {
  await new Promise((resolve) => setTimeout(resolve, 500)); // simulate loading
  try {
    const requestCookies = await cookies();
    const response = await axios({
      url,
      ...config,
      withCredentials: true,
      headers: {
        Cookie: requestCookies.toString(),
      },
      baseURL: BASE_URL,
    });

    return response.data as T;
  } catch (error: any) {
    if (error.status === 403) {
      await logout();
    }
    if (axios.isAxiosError(error) && error.response) {
      if (error.response.data) {
        throw {
          code: error.response.data.errorCode,
          message: error.response.data.errorMessage,
        };
      }
      throw {
        message: error.message,
      };
    }

    console.error(error);
    throw { message: "Internal Server Error" };
  }
};

export default fetcher;
