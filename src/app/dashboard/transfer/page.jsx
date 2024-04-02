"use client";
import React, { useState } from "react";
import styles from "@/app/ui/dashboard/transfers/transfer.module.css";

const TransferForm = () => {
  const [transfers, setTransfers] = useState([]);
  const [formData, setFormData] = useState({
    amount: "",
    reason: "",
    effectDate: "",
    registerDate: "",
    recipientAccount: "",
    groupTransfer: false,
  });

  const [cancelledTransferId, setCancelledTransferId] = useState(null);

  const handleCancel = (transferId) => {
    setTransfers(prevTransfers =>
      prevTransfers.map(transfer =>
        transfer.id === transferId ? { ...transfer, status: "cancelled" } : transfer
      )
    );
    setCancelledTransferId(transferId);
  };

  const onSubmit = (e) => {
    e.preventDefault();
    const reference = generateUniqueReference();
    const dataWithReference = { ...formData, reference, id: Date.now(), status: "pending" };
    setTransfers((prevTransfers) => [...prevTransfers, dataWithReference]);
    setFormData({
      amount: "",
      reason: "",
      effectDate: "",
      registerDate: "",
      recipientAccount: "",
      groupTransfer: false,
    });
  };

  const generateUniqueReference = () => {
    return "VIR_" + new Date().toISOString();
  };

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  return (
    <div>
        <div className={styles.container}>
            <form onSubmit={onSubmit} className={styles.form}>
                <div className={styles.section}>
                <label htmlFor="amount">Amount :</label>
                <input
                    id="amount"
                    type="number"
                    name="amount"
                    value={formData.amount}
                    onChange={handleChange}
                    required
                />
                </div>
                <div className={styles.section}>
                <label htmlFor="reason">Reason :</label>
                <input
                    id="reason"
                    type="text"
                    name="reason"
                    value={formData.reason}
                    onChange={handleChange}
                    required
                />
                </div>
                <div className={styles.section}>
                <label htmlFor="effectDate">Effective date :</label>
                <input
                    id="effectDate"
                    type="date"
                    name="effectDate"
                    value={formData.effectDate}
                    onChange={handleChange}
                    required
                />
                </div>
                <div className={styles.section}>
                <label htmlFor="registerDate">Registration date :</label>
                <input
                    id="registerDate"
                    type="date"
                    name="registerDate"
                    value={formData.registerDate}
                    onChange={handleChange}
                    required
                />
                </div>
                <div className={styles.section}>
                <label htmlFor="recipientAccount">Recipient account :</label>
                <select
                    id="recipientAccount"
                    name="recipientAccount"
                    value={formData.recipientAccount}
                    onChange={handleChange}
                    required
                >
                    <option value="">Select recipient account</option>
                    <option value="sameBank">Same bank (X)</option>
                    <option value="otherBank">Other bank (BMOI)</option>
                </select>
                </div>
                <div>
                <label htmlFor="groupTransfer">Group transfer / Scheduled :</label>
                <input
                    id="groupTransfer"
                    type="checkbox"
                    name="groupTransfer"
                    checked={formData.groupTransfer}
                    onChange={handleChange}
                />
                </div>
                <div className={styles.section}>
                <button type="submit">Confirm transfer</button>
                </div>
            </form>  
        </div>
        <div className={styles.container}>
        <h2>Lists of transfers</h2>
        {transfers.map((transfer) => (
          <div key={transfer.id} className="border p-4 my-2">
            <p>Amount : {transfer.amount}</p>
            <p>Reason : {transfer.reason}</p>
            <p>Effective Date : {transfer.effectDate}</p>
            <p>Registration date : {transfer.registerDate}</p>
            {transfer.status === "done" ? (
              <p>Statut : Done</p>
            ) : (
              <>
                <p>Statut : Loading</p>
                {new Date(transfer.effectDate) > new Date() && (
                  <button onClick={() => handleCancel(transfer.id)}>
                    Cancel
                  </button>
                )}
                {cancelledTransferId === transfer.id && (
                  <p>Transfer cancelled</p>
                )}
              </>
            )}
          </div>
        ))}
      </div>
    </div>
  );
};

export default TransferForm;
