'use client'
import './home.css'
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { csrfFetch } from "./lib/fetchWarpper";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCoffee } from '@fortawesome/free-solid-svg-icons'
import Image from 'next/image';
import headphone from '@/public/img/headphone.jpg'
import Card from './Component/Card';


export interface Category {
  id: number;
  name: string;
}

export interface Product {
  id: number;
  uuid: string;
  name: string;
  price: number;
  category: Category;
}

export interface Cart {
  price: number;
  currency: string;
  quantity: number;
}

function Home() {
  const router = useRouter();
  const [items, setItems] = useState<Product[]>([])
  const [cart, setCart] = useState<Cart>();



  useEffect(() => {
    getItems()
    // getUser()
    
  }, [])


  async function getUser() {
    const res = await csrfFetch('http://localhost:8080/api/auth/user', {
      method: 'GET',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
    })
    const data = await res.json()
  }

  async function getItems() {
    const res = await csrfFetch('http://localhost:8080/api/product', {
      method: 'GET',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
    })
    const data = await res.json()

    setItems(data)
  }
  return (
    <div className='container'>
      <div className='header'>
        <div className='header-left'>
          <div>
            <FontAwesomeIcon icon={faCoffee} size="2x" /> </div>

          <Link href={'/transitions'} className='left-btn'>Recent Payment</Link>
          <Link href={'/about'} className='left-btn'>About</Link>

        </div>
        <div className='header-right'>
          <Link href={'/register'} className='right-btn'>Register</Link>
          <Link href={'/login'} className='right-btn'>Login</Link>
        </div>
      </div>
      <div className='bloc'>
        {items?.map((item) => (
          <Card item={item} key={item.id}/>
        ))}
      </div>
    </div>
  )
}
export default Home;