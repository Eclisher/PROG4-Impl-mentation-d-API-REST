import React, { useState } from 'react';
import './ConnexionPage.css'; 
import { useNavigate } from 'react-router-dom'; 
import axios from 'axios';

function ConnexionPage() {
  const [accountNumber, setAccountNumber] = useState('');
  const [clientLastName, setClientLastName] = useState('');
  const [clientFirstName, setClientFirstName] = useState('');
  const [clientDateOfBirth, setClientDateOfBirth] = useState('');
  const [password, setPassword] = useState('');
  const [isSignUp, setIsSignUp] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const navigate = useNavigate();

  const handleSignUp = async (e) => {
    e.preventDefault();

    if (!accountNumber || !clientLastName || !clientFirstName || !clientDateOfBirth || !password) {
      setErrorMessage('Please fill in all fields.');
      return;
    }
    try {
      const response = await axios.post('http://localhost:8080/sign-up', {
        accountNumber,
        clientLastName,
        clientFirstName,
        clientDateOfBirth,
        password
      });

      if (response.status === 200) {
        setSuccessMessage('Sign-up successful');
        setErrorMessage('');
      }
    } catch (error) {
      console.error('Error signing up:', error);
      setErrorMessage('An error occurred. Please try again later.');
      setSuccessMessage('');
    }
  };

  const handleSignIn = async (e) => {
    e.preventDefault();
  
    if (!accountNumber || !password) {
      setErrorMessage('Please fill in all fields.');
      return;
    }
  
    try {
      const response = await axios.post('http://localhost:8080/sign-in', {
        accountNumber,
        password
      });
  
      if (response.status === 200) {
        setSuccessMessage('Sign-in successful');
        setErrorMessage('');
        navigate('/HomePage');
      }
    } catch (error) {
      console.error('Error signing in:', error);
      setErrorMessage('Invalid account number or password.');
      setSuccessMessage('');
    }
  };

  const toggleForm = () => {
    setIsSignUp(!isSignUp);
  };

  return (
    <div className={`container ${isSignUp ? 'active' : ''}`}>
      <div className="form-container sign-up">
        <form>
         <h1 className="TitleBank">Bank of Mada</h1>
          <h3>Create Account</h3>
          <input type="text" placeholder="Account Number" value={accountNumber} onChange={(e) => setAccountNumber(e.target.value)}/>
          <input type="text" placeholder="Last Name" value={clientLastName} onChange={(e) => setClientLastName(e.target.value)}/>
          <input type="text" placeholder="First Name" value={clientFirstName} onChange={(e) => setClientFirstName(e.target.value)}/>
          <input type="date" placeholder="Date of Birth" value={clientDateOfBirth} onChange={(e) => setClientDateOfBirth(e.target.value)}/>
          <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)}/>
          {errorMessage && <p className="error">{errorMessage}</p>}
          {successMessage && <p className="success">{successMessage}</p>}
          <button onClick={handleSignUp}>Sign Up</button>
        </form>
      </div>
      <div className="form-container sign-in">
        <form onSubmit={handleSignIn}>
          <h1>Sign In</h1>
          <div className="social-icons">
          </div>
          <span>Sign in with your account number and password</span>
          <input type="text" placeholder="Account Number" value={accountNumber} onChange={(e) => setAccountNumber(e.target.value)} />
          <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
          <a href="#">Forget Your Password?</a>
          {errorMessage && <p className="error">{errorMessage}</p>}
          <button type="submit">Sign In</button>
        </form>
      </div>
      <div className="toggle-container">
        <div className="toggle">
          <div className="toggle-panel toggle-left">
            <h1>Welcome Back!</h1>
            <p>You can subscribe to "Bank of Mada" by completing the form next to it.</p>
            <button className="hidden" onClick={toggleForm}>Sign In</button>
          </div>
          <div className="toggle-panel toggle-right">
            <h1>Hello, Friend!</h1>
            <p>Register with your personal details to unlock all the features of Bank Of Mada.</p>
            <button className="hidden" onClick={toggleForm}>Sign Up</button>
          </div>
        </div>
      </div>  
    </div>
  );
}

export default ConnexionPage;
