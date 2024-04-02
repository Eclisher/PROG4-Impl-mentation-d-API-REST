import React from 'react';

const AccountStatement = ({ transactions }) => {
  return (
    <div>
      <h2>Account Statement</h2>
      {transactions?.map((transaction, index) => (
        <div key={index}>
          <p>Date: {transaction.date}</p>
          <p>Reference: {transaction.reference}</p>
          <p>Motif: {transaction.motif}</p>
          <p>Credit MGA: {transaction.credit} MGA</p>
          <p>Debit MGA: {transaction.debit} MGA</p>
          <p>Solde: {transaction.balance} MGA</p>
          <hr />
        </div>
      ))}
    </div>
  );
};

export default AccountStatement;
