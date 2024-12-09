import axios, { AxiosRequestConfig } from "axios";

const BASE_URL = process.env.NEXT_PUBLIC_SERVER_HOST + "/api/v1";

const fetcher = async <T>(url: string, config?: AxiosRequestConfig) => {
  try {
    const response = await axios({ url, ...config, withCredentials: true, baseURL: BASE_URL });

    return response.data as T;
  } catch (error) {
    if (axios.isAxiosError(error) && error.response) {
      if (error.response.data) {
        throw error.response.data;
      }
      throw {
        errorMessage: error.message,
      };
    }
  }
};

export default fetcher;
