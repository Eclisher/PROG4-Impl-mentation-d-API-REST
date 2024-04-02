CREATE TABLE Statement (
                           statementID SERIAL PRIMARY KEY,
                           transactionDateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           reference VARCHAR(50),
                           transactionType VARCHAR(20),
                           amount DECIMAL,
                           principalBalance DECIMAL,
                           accountID INT REFERENCES Account(accountID)
);