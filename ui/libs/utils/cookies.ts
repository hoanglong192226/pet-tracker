import moment from "moment";

const JWT_SECRET = process.env.JWT_SECRET || "";
const COOKIES_PREFIX = "PET_TRACKER.";

const signCookie = async (obj: any) => {
  obj = JSON.stringify(obj);
  const encoder = new TextEncoder();
  const key = await crypto.subtle.importKey("raw", encoder.encode(JWT_SECRET), { name: "HMAC", hash: "SHA-256" }, false, ["sign"]);

  const signature = await crypto.subtle.sign("HMAC", key, encoder.encode(obj));

  return `${obj}.${Buffer.from(signature).toString("base64url")}`;
};

const unsignCookie = async (value: string) => {
  const [data, signature] = value.split(".");

  const encoder = new TextEncoder();
  const key = await crypto.subtle.importKey("raw", encoder.encode(JWT_SECRET), { name: "HMAC", hash: "SHA-256" }, false, ["verify"]);

  const isValid = await crypto.subtle.verify("HMAC", key, Buffer.from(signature, "base64url"), encoder.encode(data));
  if (!isValid) {
    throw new Error("Invalid signature");
  }

  return data;
};

const setCookie = (name: string, value: string, exdays: number) => {
  if (!(typeof window !== "undefined")) {
    return;
  }
  const expires = generateExpiredDatetime(moment().add(exdays, "d").valueOf());
  document.cookie = COOKIES_PREFIX + name + "=" + value + ";" + expires + "path=/";
};

const generateExpiredDatetime = (timestamp: number) => {
  const date = new Date(timestamp);
  const expires = "expires=" + date.toUTCString() + ";";

  return expires;
};

const getCookie = (cname: string) => {
  if (!(typeof window !== "undefined")) {
    return null;
  }
  const name = COOKIES_PREFIX + cname + "=";
  const cookies = document.cookie.split(";");

  for (let i = 0; i < cookies.length; i++) {
    let cookie = cookies[i];
    while (cookie.charAt(0) === " ") {
      cookie = cookie.substring(1);
    }

    if (cookie.indexOf(name) === 0) {
      return cookie.substring(name.length, cookie.length);
    }
  }

  return "";
};

const getCookieFromString = (cname: string, cookiesString: string) => {
  if (!cookiesString) {
    return "";
  }

  const name = COOKIES_PREFIX + cname + "=";
  const cookies = cookiesString.split(";");

  for (let i = 0; i < cookies.length; i++) {
    let cookie = cookies[i];
    while (cookie.charAt(0) === " ") {
      cookie = cookie.substring(1);
    }

    if (cookie.indexOf(name) === 0) {
      return cookie.substring(name.length, cookie.length);
    }
  }

  return "";
};

const eraseCookie = (name: string) => {
  if (!(typeof window !== "undefined")) {
    return;
  }
  setCookie(name, "", -1);
};

const removeAllCookies = () => {
  document.cookie.split(";").forEach((name) => {
    if (name.includes(COOKIES_PREFIX)) {
      eraseCookie(name.split(COOKIES_PREFIX)[1]);
    }
  });
};

const CookiesUtil = {
  setCookie,
  generateExpiredDatetime,
  getCookie,
  getCookieFromString,
  eraseCookie,
  removeAllCookies,
  COOKIES_PREFIX,
  signCookie,
  unsignCookie,
};

export default CookiesUtil;
