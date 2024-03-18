    CREATE TABLE Account (
    accountID serial PRIMARY KEY,
    accountNumber VARCHAR(20) UNIQUE,
    clientLastName VARCHAR(50),
    clientFirstName VARCHAR(50),
    password VARCHAR(80),
    clientDateOfBirth DATE,
    monthlyNetSalary DECIMAL,
    creationDate TIMESTAMP,
    modificationDate TIMESTAMP,
    overdraftEnabled BOOLEAN,
    overdraftLimit DECIMAL,
    interestRateInitial DECIMAL,
    interestRateSubsequent DECIMAL,
    maxOverdraftDays INT
);