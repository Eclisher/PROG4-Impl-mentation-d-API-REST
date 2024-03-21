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
    ALTER TABLE Account
        ALTER COLUMN creationDate SET DEFAULT now();

    CREATE OR REPLACE FUNCTION update_modification_date()
        RETURNS TRIGGER AS $$
    BEGIN
        NEW.modificationDate = now();
        RETURN NEW;
    END;
    $$ LANGUAGE plpgsql;

    CREATE TRIGGER trigger_update_modification_date
        BEFORE UPDATE ON Account
        FOR EACH ROW EXECUTE FUNCTION update_modification_date();
