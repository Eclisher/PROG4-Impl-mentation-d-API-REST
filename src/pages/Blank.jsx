// File: Blank.jsx

import React, { useState } from "react";
import "./WithdrawForm.scss";

const Blank = () => {
  const [selectedAccount, setSelectedAccount] = useState("");
  const [withdrawalDate, setWithdrawalDate] = useState("");
  const [withdrawalTime, setWithdrawalTime] = useState("");
  const [amount, setAmount] = useState("");
  const [message, setMessage] = useState("");

  const handleWithdrawal = () => {
    // Simulation of withdrawal logic
    const balance = 1000; // Example balance, replace with actual balance from selected account
    const creditLimit = 500; // Example credit limit, replace with actual credit limit from selected account
    const withdrawalAmount = parseFloat(amount);

    if (balance + creditLimit >= withdrawalAmount) {
      setMessage("Withdrawal successful!");
    } else {
      setMessage("Insufficient funds for withdrawal.");
    }
  };

  return (
    <div className="withdraw-form">
      <h2>Withdrawal Form</h2>
      <form onSubmit={handleWithdrawal}>
        <div className="form-group">
          <label htmlFor="account">Select Account:</label>
          <select
            id="account"
            value={selectedAccount}
            onChange={(e) => setSelectedAccount(e.target.value)}
          >
            {/* Options for selecting account */}
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="date">Date:</label>
          <input
            type="date"
            id="date"
            value={withdrawalDate}
            onChange={(e) => setWithdrawalDate(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label htmlFor="time">Time:</label>
          <input
            type="time"
            id="time"
            value={withdrawalTime}
            onChange={(e) => setWithdrawalTime(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label htmlFor="amount">Amount:</label>
          <input
            type="number"
            id="amount"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
          />
        </div>
        <button type="submit">Withdraw</button>
      </form>
      {message && <div className="message">{message}</div>}
    </div>
  );
};

export default Blank;
