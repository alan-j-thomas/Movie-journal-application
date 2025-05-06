import React, { useState } from 'react';
import axios from 'axios';
import '../components/css/auth.css'
import { useNavigate } from 'react-router-dom';
import { jwtDecode } from 'jwt-decode';

function Login({ onLogin }) {
  const [form, setForm] = useState({ username: '', password: '' });
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {

      const res = await axios.post('http://localhost:9898/auth/login', form, {
        headers: {
          'Content-Type': 'application/json',
        },
      });
      const { token } = res.data;
      localStorage.setItem('token', token);
      const user = jwtDecode(token);
      onLogin(user);

      navigate('/');
    } catch (err) {
      setError('Invalid email or password');
    }
  };

  return (
    <div className='outer-container'>
      <div className="auth-container">
        <h2>Login</h2>
        {error && <p className="error-msg">{error}</p>}
        <form onSubmit={handleSubmit} className="auth-form">
          <input type="text" name="username" placeholder="UserName" value={form.username} onChange={handleChange} required />
          <input type="password" name="password" placeholder="Password" value={form.password} onChange={handleChange} required />
          <button type="submit">Login</button>
        </form>
      </div>
    </div>

  );
}

export default Login;
