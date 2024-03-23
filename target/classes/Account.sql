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
    interst DECIMAL,
    maxOverdraftDays INT
    CONSTRAINT chk_overdraftLimit CHECK (overdraftLimit >= 0),
    CONSTRAINT chk_interestRateInitial CHECK (interestRateInitial >= 0),
    CONSTRAINT chk_interestRateSubsequent CHECK (interestRateSubsequent >= 0),
    CONSTRAINT chk_maxOverdraftDays CHECK (maxOverdraftDays >= 0),
    CONSTRAINT chk_dateOfBirth CHECK (clientDateOfBirth <= CURRENT_DATE - INTERVAL '21 years'),
    UNIQUE (clientLastName, clientFirstName, clientDateOfBirth)
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
