CREATE TABLE TransactionCategory (
    transactionCategoryID INT PRIMARY KEY,
    transactionID INT,
    categoryID INT,
    FOREIGN KEY (transactionID) REFERENCES Transaction(transactionID),
    FOREIGN KEY (categoryID) REFERENCES Category(categoryID)
);