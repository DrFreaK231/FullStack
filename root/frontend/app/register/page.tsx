"use client"
import Image from "next/image";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { csrfFetch } from "../lib/fetchWarpper"; 

interface userDetails {
  username: string,
  password: string,
  phoneNumber: string,
  email: string
}

export default function Register() {

  const router = useRouter();

  const [username, setUsername] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [conPassword , setConPassword] = useState<string>('');
  const [phoneNumber, setPhoneNumber] = useState<string>('');
  const [email, setEmail] = useState<string>('');


  async function createUser() {
    const user: userDetails = { username, password, phoneNumber, email };

    const res = await csrfFetch('http://localhost:8080/api/auth/register', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      credentials: "include",
      body: JSON.stringify(user)
    });
    if (res.ok){
      router.push("/login")
    }
    const data = await res.json();
  }
  

  function userHandler(){
    if (conPassword == password){
      createUser()
    }else{
      throw new Error("Password doesn't match")
    }
  }

  


  return (
    <div>
      <div>
        <div>
          <label>Username :</label>
          <input type="text" placeholder="Your username" onChange={(e)=>setUsername(e.target.value)}></input>
        </div>
        <div>
          <label>Email :</label>
          <input type="email" placeholder="Your Email Address" onChange={(e)=>setEmail(e.target.value)}></input>
        </div>
        <div>
          <label>Phone Number :</label>
          <input type="tel" placeholder="Your Current PhoneNumber" onChange={(e)=>setPhoneNumber(e.target.value)}></input>
        </div>
        <div>
          <label>Password :</label>
          <input type="password" placeholder="Your Password" onChange={(e)=>setPassword(e.target.value)}></input>
        </div>
        <div>
          <label>Confirm Password</label>
          <input type="password" placeholder="Type Identical Password" onChange={(e)=>setConPassword(e.target.value)}></input>
        </div>
      </div>
      <div>
        <button onClick={userHandler}>Register</button>
      </div>
      <div>
        <button onClick={()=>window.location.href = "http://localhost:8080/oauth2/authorization/google"}>Login with Google</button>
        <button onClick={()=>window.location.href = "http://localhost:8080/oauth2/authorization/github"}>Login with Github</button>

      </div>
    </div>
  );
}
