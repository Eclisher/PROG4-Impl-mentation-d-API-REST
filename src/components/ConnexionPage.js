import { useState } from 'react';
import { useRouter } from 'next/router';
import axios from 'axios';
import styles from './ConnexionPage.module.css';

export default function ConnexionPage() {
  const [accountNumber, setAccountNumber] = useState('');
  const [clientLastName, setClientLastName] = useState('');
  const [clientFirstName, setClientFirstName] = useState('');
  const [clientDateOfBirth, setClientDateOfBirth] = useState('');
  const [password, setPassword] = useState('');
  const [isSignUp, setIsSignUp] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const router = useRouter();

  const handleSignUp = async (e) => {
    e.preventDefault();

    if (!accountNumber || !clientLastName || !clientFirstName || !clientDateOfBirth || !password) {
      setErrorMessage('Please fill in all fields.');
      return;
    }
    try {
      const response = await axios.post('/api/sign-up', {
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
      const response = await axios.post('/api/sign-in', {
        accountNumber,
        password
      });
  
      if (response.status === 200) {
        setSuccessMessage('Sign-in successful');
        setErrorMessage('');
        router.push('/dashboard');
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
    <div className={styles.__reset__}>
      <div className={styles.body_container}>
    <div className={`${styles.container} ${isSignUp ? styles.active : ''}`}>
    <div className={`${styles['form-container']} ${styles['sign-up']}`}>
        <form>
          <h1 className={styles.TitleBank}>Bank of Mada</h1>
          <h3>Create Account</h3>
          <input type="text" placeholder="Account Number" value={accountNumber} onChange={(e) => setAccountNumber(e.target.value)}/>
          <input type="text" placeholder="Last Name" value={clientLastName} onChange={(e) => setClientLastName(e.target.value)}/>
          <input type="text" placeholder="First Name" value={clientFirstName} onChange={(e) => setClientFirstName(e.target.value)}/>
          <input type="date" placeholder="Date of Birth" value={clientDateOfBirth} onChange={(e) => setClientDateOfBirth(e.target.value)}/>
          <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)}/>
          {errorMessage && <p className={styles.error}>{errorMessage}</p>}
          {successMessage && <p className={styles.success}>{successMessage}</p>}
          <button onClick={handleSignUp}>Sign Up</button>
        </form>
      </div>
      <div className={`${styles['form-container']} ${styles['sign-in']}`}>

        <form onSubmit={handleSignIn}>
          <h1>Sign In</h1>
          <span>Sign in with your account number and password</span>
          <input type="text" placeholder="Account Number" value={accountNumber} onChange={(e) => setAccountNumber(e.target.value)} />
          <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
          {errorMessage && <p className={styles.error}>{errorMessage}</p>}
          <button type="submit">Sign In</button>
        </form>
      </div>
      <div className={styles['toggle-container']}>
        <div className={styles.toggle}>
          <div className={`${styles['toggle-panel']} ${styles['toggle-left']}`}>
            <h1>Welcome Back!</h1>
            <p>You can subscribe to "Bank of Mada" by completing the form next to it.</p>
            <button className={styles.hidden} onClick={toggleForm}>Sign In</button>
          </div>
          <div className={`${styles['toggle-panel']} ${styles['toggle-right']}`}>
            <h1>Hello, Friend!</h1>
            <p>Register with your personal details to unlock all the features of Bank Of Mada.</p>
            <button className={styles.hidden} onClick={toggleForm}>Sign Up</button>
          </div>
        </div>
      </div>  
    </div>
   </div> 
  </div> 
  );
}
