CREATE TABLE Transfer (
                          transferID SERIAL PRIMARY KEY,
                          transferDateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          amount DECIMAL,
                          transferType VARCHAR(20),
                          transferReason VARCHAR(100),
                          sourceAccountID INT REFERENCES Account(accountID),
                          destinationAccountID INT REFERENCES Account(accountID),
                          status VARCHAR(20),
                          scheduledDateTime TIMESTAMP,
                          cancelledDateTime TIMESTAMP,
                          FOREIGN KEY (sourceAccountID) REFERENCES Account(accountID),
                          FOREIGN KEY (destinationAccountID) REFERENCES Account(accountID)
);