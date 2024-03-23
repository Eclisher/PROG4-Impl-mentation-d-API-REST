CREATE TABLE Transaction (
                             transactionID SERIAL PRIMARY KEY,
                             transactionDateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             amount DECIMAL,
                             transactionType VARCHAR(20),
                             accountID INT REFERENCES Account(accountID),
                             categoryID INT,
                             FOREIGN KEY (accountID) REFERENCES Account(accountID),
                             FOREIGN KEY (categoryID) REFERENCES Category(categoryID)
);