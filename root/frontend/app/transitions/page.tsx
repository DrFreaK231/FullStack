'use client'
import { useEffect, useState } from "react"
import { csrfFetch } from "../lib/fetchWarpper";


export interface Order {
    orderId: number;
    amount: number;
    currency: string;
    orderSessionId: string;
    payerId: string;
    paymentId: string;
    provider: string;
}


function Transitions() {

    const [orders, setOrders] = useState<Order[]>([])

    useEffect(() => {
        getOrders()
    }, [])

    async function getOrders() {
        const res = await csrfFetch('http://localhost:8080/api/orders', {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' },
            credentials: "include",
        });

        const data: Order[] = await res.json()

        console.log(data)
        setOrders(data)
    }

    return (
        <div>
            <h1>Orders</h1>

            {orders.length === 0 && <p>No orders found.</p>}

            <ul>
                {orders.map((order) => (
                    <li key={order.orderId} className="border p-4 m-2">
                        <p><b>Order ID:</b> {order.orderId}</p>
                        <p><b>Amount:</b> {order.amount}</p>
                        <p><b>Currency:</b> {order.currency}</p>
                        <p><b>Provider:</b> {order.provider}</p>
                        <p><b>Payment ID:</b> {order.paymentId}</p>
                        <p><b>Payer ID:</b> {order.payerId}</p>
                        <p><b>Session ID:</b> {order.orderSessionId}</p>
                    </li>
                ))}
            </ul>
        </div>
    )
}

export default Transitions
