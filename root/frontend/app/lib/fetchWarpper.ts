
import { getCsrfToken } from "./csrf";
export async function csrfFetch(input: RequestInfo, init?: RequestInit) {
  const token = await getCsrfToken();
  const csrfHeader = token || "";

  const existingHeaders: Record<string, string> =
    init?.headers instanceof Headers
      ? Object.fromEntries(init.headers.entries())
      : Array.isArray(init?.headers)
      ? Object.fromEntries(init.headers)
      : init?.headers || {};

  const headers: Record<string, string> = {
    ...existingHeaders,
    "Content-Type": "application/json",
    "X-XSRF-TOKEN": csrfHeader,
  };

  return fetch(input, {
    ...init,
    headers,
    credentials: "include",
  });
}