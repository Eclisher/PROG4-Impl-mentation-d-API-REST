CREATE TABLE Balance (
                         accountID INT PRIMARY KEY REFERENCES Account(accountID),
                         principalBalance DECIMAL,
                         loanAmount DECIMAL,
                         loanInterest DECIMAL,
                         FOREIGN KEY (accountID) REFERENCES Account(accountID)
);