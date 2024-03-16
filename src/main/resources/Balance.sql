CREATE TABLE Balance (
    accountID INT PRIMARY KEY,
    principalBalance DECIMAL,
    loanAmount DECIMAL,
    loanInterest DECIMAL,
    FOREIGN KEY (accountID) REFERENCES Account(accountID)
);
