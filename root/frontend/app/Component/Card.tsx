import { Product } from "../page"
import Image from 'next/image';
import headphone from '@/public/img/headphone.jpg'
import { useState } from "react";
import '../home.css'
import { csrfFetch } from "../lib/fetchWarpper";

interface CardProps {
    item: Product;
}

function Card({ item }: CardProps) {
    const [quantity, setQuantity] = useState<number>(0)

    async function purchaseHandler() {
        if (quantity <= 0) {
            alert("Please select quantity");
            return;
        }

        const body = {
            email: "",
            productUuid: item.uuid,
            quantity: quantity,
            amount: item.price,
            currency: "SGD",
            orderSessionId: ""
        };

        // 1️⃣ create order in your main server
        const orderRes = await csrfFetch('http://localhost:8080/api/orders/create', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include',
            body: JSON.stringify(body)
        });
        const { orderUuid } = await orderRes.json();
        body.orderSessionId = orderUuid;

        if (!orderRes.ok) {
            alert("Failed to create order.");
            return;
        }

        // 2️⃣ call PayPal create
        const paypalRes = await csrfFetch('http://localhost:8080/paypal/create', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include',
            body: JSON.stringify(body)
        });


        // 3️⃣ read JSON response
        const data = await paypalRes.json();

        const approveUrl = data.approveUrl;
        const sessionId = data.sessionId;

        if (!approveUrl) {
            alert("PayPal approval URL missing");
            return;
        }


        window.location.href = approveUrl + `&sessionId=${sessionId}`;

    }


    return (
        <div key={item.id} className='card'>
            <div className='card-img'>
                <Image src={headphone} style={{ objectFit: "cover" }}
                    alt=''
                    priority />
            </div>
            <div className='card-text'>
                <div>{item.name} Category: {item.category.name}</div>
                <div>Each: ${item.price}</div>
                <div>Quantity: {quantity}</div>
            </div>
            <div className="card-btn">
                <button onClick={() => setQuantity(e => e + 1)}>+</button>
                <button onClick={() => setQuantity(e => e - 1)}>-</button>
                <button onClick={purchaseHandler}>Buy</button>
            </div>
        </div>
    )
}
export default Card