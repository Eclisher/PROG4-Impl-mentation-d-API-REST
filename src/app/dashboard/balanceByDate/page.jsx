"use client"
import React, { useState } from 'react';

const DateSelector = ({ onSubmit }) => {
  const [selectedDate, setSelectedDate] = useState(new Date().toISOString().split('T')[0]);
  const [balances, setBalances] = useState(null); // State to hold balances

  const handleChangeDate = (e) => {
    setSelectedDate(e.target.value);
  };

  const handleSubmit = async (e) => { // Make it asynchronous to handle API calls
    e.preventDefault();
    const balancesForDate = await onSubmit(selectedDate); // Fetch balances for selected date
    setBalances(balancesForDate);
  };

  return (
    <div>
      <h2>Select Date to Check Balance</h2>
      <form onSubmit={handleSubmit}>
        <label htmlFor="dateSelect">Choose a date: </label>
        <input
          type="date"
          id="dateSelect"
          value={selectedDate}
          onChange={handleChangeDate}
        />
        <button type="submit">Check Balance</button>
      </form>

      {balances && (
        <div>
          <h2>Balance for {selectedDate}</h2>
          <ul>
            <li>Main balance: {balances.principal}</li>
            <li>Loans: {balances.loans}</li>
            <li>Interest: {balances.interest}</li>
          </ul>
        </div>
      )}
    </div>
  );
};

export default DateSelector;
