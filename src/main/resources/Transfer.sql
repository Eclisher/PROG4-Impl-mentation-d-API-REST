
CREATE TABLE Transfer (
    transferID INT PRIMARY KEY,
    transferDateTime DATETIME,
    amount DECIMAL,
    transferType VARCHAR(20),
    transferReason VARCHAR(100),
    sourceAccountID INT,
    destinationAccountID INT,
    status VARCHAR(20),
    scheduledDateTime DATETIME,
    cancelledDateTime DATETIME,
    FOREIGN KEY (sourceAccountID) REFERENCES Account(accountID),
    FOREIGN KEY (destinationAccountID) REFERENCES Account(accountID)
);
