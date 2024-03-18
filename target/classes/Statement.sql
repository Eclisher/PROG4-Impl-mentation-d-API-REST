CREATE TABLE Statement (
    statementID INT PRIMARY KEY,
    transactionDateTime DATETIME,
    reference VARCHAR(50),
    transactionType VARCHAR(20),
    amount DECIMAL,
    principalBalance DECIMAL,
    accountID INT,
    FOREIGN KEY (accountID) REFERENCES Account(accountID)
);