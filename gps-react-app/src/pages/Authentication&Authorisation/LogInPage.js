import React, { useEffect, useRef, useState } from 'react'
import { useNavigate } from 'react-router'
import useAuth from '../../hooks/useAuth'
import jwt_decode from "jwt-decode"
import logo from '../../../src/images/ivanti-logo.svg';
import "../../styles/Authentication&Authorisation/login.css";

import axios from "axios"
const URL = 'http://localhost:8080/login'

const LoginPage = () => {

    const { setAuth } = useAuth();
    const navigate = useNavigate();
    const usernameRef = useRef();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    useEffect(() => {
        usernameRef.current.focus();
    }, [])

    function storeUsername(username){
        setUsername(username)
        
    }

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post(URL,
                JSON.stringify({username, password}),
                {
                    headers: {'Content-Type': 'application/json'}
                }
            );

            const accessToken = response?.data?.accessToken;
            const decoded = jwt_decode(accessToken);
            const roles = decoded?.roles;
            console.log(roles)
            setAuth({ accessToken, decoded, roles });
            localStorage.setItem("username", username);

            setUsername('')
            setPassword('')

            navigate('/')
        } catch (err) {
            console.log(err)
        }
    }

  return (
    <>
        <div className='LogIn'>
            <h1>Log In</h1>
            <form onSubmit={handleSubmit} className={'LogInForm'}>
                <input
                    type={'username'}
                    placeholder='Username *'
                    ref={usernameRef}
                    value={username}
                    onChange={(e) => {setUsername(e.target.value)}}
                />
                <input
                    type={'password'}
                    placeholder='Password *'
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <button>Log In</button>
            </form>
        </div>
    </>
  )
}

export default LoginPage
