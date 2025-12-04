// app/payment/success/page.tsx
"use client";

import { useEffect, useState } from "react";
import { useSearchParams, useRouter } from "next/navigation";

export default function PaymentSuccessPage() {
  const searchParams = useSearchParams();
  const router = useRouter();
  const sessionId = searchParams.get("sessionId");

  const [status, setStatus] = useState<"loading" | "success" | "error">("loading");
  const [message, setMessage] = useState("");

  useEffect(() => {
    if (!sessionId) {
      setStatus("error");
      setMessage("Missing sessionId.");
      return;
    }

    // Here you can optionally call backend to verify the session/payment
    setStatus("success");
    setMessage("Payment confirmed successfully!");
  }, [sessionId]);

  return (
    <div style={{ padding: 40, textAlign: "center" }}>
      {status === "loading" && <h1>Processing payment...</h1>}
      {status === "success" && (
        <>
          <h1>🎉 Payment Successful!</h1>
          <p>{message}</p>
          <button onClick={() => router.push("/")}>Go Home</button>
        </>
      )}
      {status === "error" && (
        <>
          <h1>❌ Payment Failed</h1>
          <p>{message}</p>
          <button onClick={() => router.push("/cart")}>Return to Cart</button>
        </>
      )}
    </div>
  );
}
