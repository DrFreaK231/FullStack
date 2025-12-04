
let csrfToken: string | null = null;

export async function getCsrfToken(): Promise<string | null> {
  if (csrfToken) return csrfToken;

  const res = await fetch("http://localhost:8080/csrf-token", {
    method: "GET",
    credentials: "include",
  });

  if (!res.ok) throw new Error("Failed to fetch CSRF token");

  const data = await res.json();
  csrfToken = data.token;
  return csrfToken;
}
