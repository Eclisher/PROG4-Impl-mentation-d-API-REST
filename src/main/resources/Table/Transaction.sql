CREATE TABLE Transaction (
                             transactionID SERIAL PRIMARY KEY,
                             transactionDateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             amount DECIMAL,
                             transactionType VARCHAR(20),
                             accountID INT REFERENCES Account(accountID),
                             categoryID INT REFERENCES Category(categoryID),
                             status VARCHAR (50),
                             reason VARCHAR (50),
                             effectDate TIMESTAMP,
                             FOREIGN KEY (accountID) REFERENCES Account(accountID),
                             FOREIGN KEY (categoryID) REFERENCES Category(categoryID)
);