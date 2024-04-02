"use client"
import React, { useState } from "react";
import styles from '@/app/ui/dashboard/interest/interest.module.css'

const InterestConfiguration = () => {
  const [interest7Days, setInterest7Days] = useState("");
  const [interestAfter7Days, setInterestAfter7Days] = useState("");
  const [errors, setErrors] = useState({});

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!interest7Days || !interestAfter7Days) {
      setErrors({
        interest7Days: !interest7Days ? "Interest rate for the first 7 days is required" : "",
        interestAfter7Days: !interestAfter7Days ? "Interest rate after 7 days is required" : "",
      });
      return;
    }

    if (isNaN(interest7Days) || interest7Days < 0 || interest7Days > 100) {
      setErrors({
        interest7Days: "Please enter a valid interest rate for the first 7 days (0-100)",
        interestAfter7Days: errors.interestAfter7Days,
      });
      return;
    }

    if (isNaN(interestAfter7Days) || interestAfter7Days < 0 || interestAfter7Days > 100) {
      setErrors({
        interest7Days: errors.interest7Days,
        interestAfter7Days: "Please enter a valid interest rate after 7 days (0-100)",
      });
      return;
    }

    // Implémenter la logique pour enregistrer les valeurs des taux d'intérêt dans la base de données
    alert("Changes saved successfully!");
  };

  return (
    <div onSubmit={handleSubmit} className={styles.container}>
      <form className={styles.from}>
        <div className={styles.section}>
          <label htmlFor="interest7Days">Interest rate for the first 7 days (%)</label>
          <input
            id="interest7Days"
            type="number"
            value={interest7Days}
            onChange={(e) => setInterest7Days(e.target.value)}
            className="w-12 ml-2 border border-black"
          />
          {errors.interest7Days && <p className="text-red-500">{errors.interest7Days}</p>}
        </div>
        <div className={styles.section}>
          <label htmlFor="interestAfter7Days">Interest rate after 7 days (%)</label>
          <input
            id="interestAfter7Days"
            type="number"
            value={interestAfter7Days}
            onChange={(e) => setInterestAfter7Days(e.target.value)}
            className="w-12 ml-2 border border-black"
          />
          {errors.interestAfter7Days && <p className="text-red-500">{errors.interestAfter7Days}</p>}
        </div>
        <button
          type="submit"
          className="p-3 text-white font-bold w-48 mt-4"
        >
          Save Changes
        </button>
      </form>
    </div>
  );
};

export default InterestConfiguration;

