'use client'
import { useRouter } from "next/navigation"
import { useEffect, useState } from "react"
import { csrfFetch } from "../lib/fetchWarpper"; 

function Login() {
  const router = useRouter();
  const [username, setUsername] = useState<string>('')
  const [password, setPassword] = useState<string>('')



  async function userLogin() {
    const res = await csrfFetch('http://localhost:8080/api/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        username: username,
        password: password
      }),
      credentials: 'include'
    });
if(res.ok){
      createUser()
}
    
  }
  async function createUser(){
    const response = await csrfFetch('http://localhost:8080/api/user/register', {
          method: 'GET',
          headers: { 'Content-Type': 'application/json' },
          credentials: 'include'
        });
        // const data = await response.json()
        // localStorage.setItem('uuid', data.uuid);
  }

  

  return (
    <div>
      <div>
        <div>
          <label>Username :</label>
          <input type="text" placeholder="Your username" onChange={(e) => setUsername(e.target.value)}></input>
        </div>
        <div>
          <label>Password :</label>
          <input type="password" placeholder="Your Password" onChange={(e) => setPassword(e.target.value)}></input>
        </div>
      </div>
      <div>
        <button onClick={userLogin}> Login</button>
      </div>
    </div>

  )
}
export default Login