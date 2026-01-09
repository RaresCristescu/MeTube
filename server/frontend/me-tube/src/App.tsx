import './App.css'
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Register from './pages/Register';
import Login from './pages/Login';
import Message from './pages/Message';
import 'bootstrap-icons/font/bootstrap-icons.css';

const App: React.FC = () => {
  return (
   <Router>
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/messages" element={<Message />} />
      <Route path="/register" element={<Register />} />
    </Routes>
   </Router>
  );
}

export default App
