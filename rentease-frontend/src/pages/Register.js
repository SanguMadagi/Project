import React, { useState } from 'react';
import API from '../api';
import { useNavigate } from 'react-router-dom';

function Register() {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();
        try {
            await API.post('/auth/register', { name, email, password });
            alert('Registered successfully');
            navigate('/login');
        } catch (err) {
            alert('Registration failed');
        }
    }

    return (
        <div>
            <h2>Register</h2>
            <form onSubmit={handleRegister}>
                <input placeholder="Name" value={name} onChange={e=>setName(e.target.value)} required />
                <input placeholder="Email" value={email} onChange={e=>setEmail(e.target.value)} required />
                <input type="password" placeholder="Password" value={password} onChange={e=>setPassword(e.target.value)} required />
                <button type="submit">Register</button>
            </form>
        </div>
    );
}

export default Register;
