CREATE TABLE TransactionCategory (
                                     transactionCategoryID SERIAL PRIMARY KEY,
                                     transactionID INT REFERENCES Transaction(transactionID),
                                     categoryID INT REFERENCES Category(categoryID)
);