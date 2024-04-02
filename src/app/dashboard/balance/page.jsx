"use client"
import React, { useState } from "react";
import styles from '@/app/ui/dashboard/balance/balance.module.css'

const BalanceDisplay = ({ balances }) => {
  const currentDate = new Date().toISOString().split("T")[0];
  const [selectedDate, setSelectedDate] = useState(currentDate);

  const handleChangeDate = (e) => {
    setSelectedDate(e.target.value);
  };

  return (
    <div>
      <div className={styles.container}>
        <div className={styles.form}>
            <div className={styles.section}>
                <label htmlFor="dateSelect">Choose a date: </label>
                <select
                id="dateSelect"
                value={selectedDate}
                onChange={handleChangeDate}
                >
                {balances &&
                    Object.keys(balances).map((date) => (
                    <option key={date} value={date}>
                        {date}
                    </option>
                    ))}
                <option value={currentDate}>{currentDate}</option>
                </select>
            </div>
        </div>
      </div>
      <div className={styles.container}>
        <h3>Balance for {selectedDate}</h3>
        {balances && balances[selectedDate] ? (
          <ul>
            <li>Main balance: {balances[selectedDate].principal}</li>
            <li>Loans: {balances[selectedDate].loans}</li>
            <li>Interest: {balances[selectedDate].interest}</li>
          </ul>
        ) : (
          <p>No balance data available for the selected date.</p>
        )}
      </div>
    </div>
  );
};

export default BalanceDisplay;
