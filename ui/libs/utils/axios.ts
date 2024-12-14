import axios, { AxiosRequestConfig } from "axios";

// const BASE_URL = process.env.NEXT_PUBLIC_SERVER_HOST + "/api/v1";
const BASE_URL = "http://app-server:8080/api/v1";

const fetcher = async <T>(url: string, config?: AxiosRequestConfig) => {
  await new Promise((resolve) => setTimeout(resolve, 500)); // simulate loading
  try {
    const response = await axios({ url, ...config, withCredentials: true, baseURL: BASE_URL });

    return response.data as T;
  } catch (error) {
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
