import React, { useState } from 'react';
import axios from 'axios';
import '../components/css/auth.css'

function Signup() {
  const [form, setForm] = useState({ name: '', email: '', password: '' });
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage('');
    setError('');

    try {
      await axios.post('/auth/signup', form);
      setMessage('Account created successfully. Please log in.');
      setForm({ name: '', email: '', password: '' });
    } catch (err) {
      setError('Signup failed. Email may already be in use.');
    }
  };

  return (
    <div className="auth-container">
      <h2>Sign Up</h2>
      {message && <p className="success-msg">{message}</p>}
      {error && <p className="error-msg">{error}</p>}
      <form onSubmit={handleSubmit} className="auth-form">
        <input type="text" name="name" placeholder="Name" value={form.name} onChange={handleChange} required />
        <input type="email" name="email" placeholder="Email" value={form.email} onChange={handleChange} required />
        <input type="password" name="password" placeholder="Password" value={form.password} onChange={handleChange} required />
        <button type="submit">Sign Up</button>
      </form>
    </div>
  );
}

export default Signup;
