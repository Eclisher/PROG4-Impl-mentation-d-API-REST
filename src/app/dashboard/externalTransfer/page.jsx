"use client"
import React, { useState } from "react";
import styles from '@/app/ui/dashboard/externalTransfer/externalTransfer.module.css'

const ExternalTransferForm = ({ onSubmit }) => {
  const [formData, setFormData] = useState({
    amount: "",
    reason: "",
    effectiveDate: "",
    registrationDate: "",
  });

  const [formSubmitted, setFormSubmitted] = useState(false);
  const [externalTransfers, setExternalTransfers] = useState([]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setFormSubmitted(true);
    if(formData.amount && formData.reason && formData.effectiveDate && formData.registrationDate){
      onSubmit(formData);
      setExternalTransfers([...externalTransfers, formData]); // Ajouter le transfert externe à la liste
      setFormData({ // Réinitialiser le formulaire après la soumission
        amount: "",
        reason: "",
        effectiveDate: "",
        registrationDate: "",
      });
    }
  };

  return (
    <div>
      <div className={styles.container}>
        <form onSubmit={handleSubmit} className={styles.form}>
          <div className={styles.section}>
            <label htmlFor="amount">Amount</label>
            <input
              id="amount"
              type="number"
              name="amount"
              value={formData.amount}
              onChange={handleChange}
            />
            {formSubmitted && !formData.amount && <p className="text-red-500">Please enter the amount.</p>}
          </div>
          <div className={styles.section}>
            <label htmlFor="reason">Reason</label>
            <input
              id="reason"
              type="text"
              name="reason"
              value={formData.reason}
              onChange={handleChange}
            />
            {formSubmitted && !formData.reason && <p className="text-red-500">Please enter the reason for the transfer.</p>}
          </div>
          <div className={styles.section}>
            <label htmlFor="effectiveDate">Effective Date</label>
            <input
              id="effectiveDate"
              type="date"
              name="effectiveDate"
              value={formData.effectiveDate}
              onChange={handleChange}
            />
            {formSubmitted && !formData.effectiveDate && <p className="text-red-500">Please enter the effective date.</p>}
          </div>
          <div className={styles.section}>
            <label htmlFor="registrationDate">Registration Date</label>
            <input
              id="registrationDate"
              type="date"
              name="registrationDate"
              value={formData.registrationDate}
              onChange={handleChange}
            />
            {formSubmitted && !formData.registrationDate && <p className="text-red-500">Please enter the registration date.</p>}
          </div>
          <button
            type="submit"
            className="bg-gray-400 p-3 text-white font-bold hover:bg-gray-600 w-64 mt-2"
          >
            Submit Transfer
          </button>
        </form>
      </div>

      <div className={styles.container}>
        <h2>External Transfer List</h2>
        <ul>
          {externalTransfers.map((transfer, index) => (
            <li key={index}>
              <p>Amount: {transfer.amount}</p>
              <p>Reason: {transfer.reason}</p>
              <p>Effective Date: {transfer.effectiveDate}</p>
              <p>Registration Date: {transfer.registrationDate}</p>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default ExternalTransferForm;

