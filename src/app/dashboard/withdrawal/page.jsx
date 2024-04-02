"use client";
import React, { useState } from "react";
import styles from '@/app/ui/dashboard/withdrawal/withdrawal.module.css'

const WithdrawalForm = ({ accounts }) => {
  const [withdrawals, setWithdrawals] = useState([]);
  const [formData, setFormData] = useState({
    accountId: "",
    amount: "",
    date: "",
    time: "",
    overdraftEnabled: false,
  });

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const selectedAccount = accounts?.find(
      (account) => account.id === parseInt(formData.accountId)
    );

    if (!selectedAccount) {
      alert("Selected account not found in provided accounts list.");
      return;
    }

    const totalAmountAvailable =
      selectedAccount.balance + selectedAccount.salary / 3;

    if (
      totalAmountAvailable >= parseFloat(formData.amount) ||
      formData.overdraftEnabled
    ) {
      const newWithdrawal = {
        accountId: formData.accountId,
        amount: parseFloat(formData.amount),
        date: formData.date,
        time: formData.time,
        overdraftEnabled: formData.overdraftEnabled,
      };
      setWithdrawals((prevWithdrawals) => [...prevWithdrawals, newWithdrawal]);
      alert("Retrait effectué avec succès");
      setFormData({
        accountId: "",
        amount: "",
        date: "",
        time: "",
        overdraftEnabled: false,
      });
    } else {
      alert(
        "Fonds insuffisants. Veuillez choisir un montant inférieur ou activer le découvert autorisé."
      );
    }
  };

  return (
    <div>
        <div className={styles.container}>
            <form
                onSubmit={handleSubmit}
                className={styles.form}
            >
                <div className={styles.section}>
                <label htmlFor="accountId">Account:</label>
                <select
                    id="accountId"
                    name="accountId"
                    value={formData.accountId}
                    onChange={handleChange}
                >
                    <option value="">Select an account</option>
                    {accounts?.map((account) => (
                    <option key={account.id} value={account.id}>
                        {account.firstName} {account.lastName} - {account.accountNumber}
                    </option>
                    ))}
                </select>
                </div>
                <div className={styles.section}>
                <label htmlFor="amount">Amount</label>
                <input
                    id="amount"
                    type="number"
                    name="amount"
                    value={formData.amount}
                    onChange={handleChange}
                />
                </div>
                <div className={styles.section}>
                <label htmlFor="date">Date</label>
                <input
                    id="date"
                    type="date"
                    name="date"
                    value={formData.date}
                    onChange={handleChange}
                />
                </div>
                <div className={styles.section}>
                <label htmlFor="time">Time</label>
                <input
                    id="time"
                    type="time"
                    name="time"
                    value={formData.time}
                    onChange={handleChange}
                />
                </div>
                <div>
                <label htmlFor="overdraftEnabled">Activate overdraft</label>
                <input
                    id="overdraftEnabled"
                    type="checkbox"
                    name="overdraftEnabled"
                    checked={formData.overdraftEnabled}
                    onChange={handleChange}
                />
                </div>
                <div className={styles.section}>
                    <button type="submit">Confirm the withdrawal</button>    
                </div>        
            </form>
        </div>
        <div className={styles.container}>
            <h2>List of Withdrawals</h2>
            {withdrawals?.map((withdrawal, index) => (
                <div key={index} className="border p-2 m-2">
                    <p>Account ID: {withdrawal.accountId}</p>
                    <p>Amount: {withdrawal.amount}</p>
                    <p>Date: {withdrawal.date}</p>
                    <p>Time: {withdrawal.time}</p>
                    <p>Overdraft Enabled: {withdrawal.overdraftEnabled.toString()}</p>
                </div>
            ))}
        </div>
        
    </div>
  );
};

export default WithdrawalForm;
