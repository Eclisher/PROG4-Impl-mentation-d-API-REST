"use client";
import { useState, useEffect } from "react";
import axios from "axios";
import styles from "@/app/ui/dashboard/products/addProduct/addProduct.module.css";

const AccountForm = () => {
  const [accounts, setAccounts] = useState([]);

  useEffect(() => {
    const fetchAccounts = async () => {
      try {
        const response = await axios.get("http://localhost:8080/accounts");
        setAccounts(response.data);
      } catch (error) {
        console.error("Error fetching accounts:", error);
      }
    };

    fetchAccounts();
  }, []);

  return (
    <div>
      <div className={styles.container}>
        <h2 className={styles.title}>List of accounts</h2>
        <table className={styles.table}>
          <thead>
            <tr>
              <th>First Name</th>
              <th>Last Name</th>
              <th>Date of Birth</th>
              <th>Monthly Net Salary</th>
              <th>Balance</th>
              <th>Account Number</th>
            </tr>
          </thead>
          <tbody>
            {accounts.map((account, index) => (
              <tr key={index}>
                <td>{account.clientFirstName}</td>
                <td>{account.clientLastName}</td>
                <td>{account.clientDateOfBirth}</td>
                <td>{account.monthlyNetSalary}</td>
                <td>{account.balance}</td>
                <td>{account.accountNumber}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default AccountForm;
