    CREATE TABLE Account (
    accountID INT PRIMARY KEY,
    accountNumber VARCHAR(20) UNIQUE,
    clientLastName VARCHAR(50),
    clientFirstName VARCHAR(50),
    clientDateOfBirth DATE,
    monthlyNetSalary DECIMAL,
    creationDate DATETIME,
    modificationDate DATETIME,
    overdraftEnabled BIT,
    overdraftLimit DECIMAL,
    interestRateInitial DECIMAL,
    interestRateSubsequent DECIMAL,
    maxOverdraftDays INT
);