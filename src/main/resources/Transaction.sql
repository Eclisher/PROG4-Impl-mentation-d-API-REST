CREATE TABLE Transaction (
    transactionID INT PRIMARY KEY,
    transactionDateTime DATETIME,
    amount DECIMAL,
    transactionType VARCHAR(20),
    accountID INT,
    FOREIGN KEY (accountID) REFERENCES Account(accountID),
    FOREIGN KEY (categoryID) REFERENCES Category(categoryID)
);