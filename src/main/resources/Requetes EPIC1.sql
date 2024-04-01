-- F1 Création d'un nouveau compte
INSERT INTO Account (accountNumber, clientLastName, clientFirstName, clientDateOfBirth, monthlyNetSalary, creationDate, modificationDate, overdraftEnabled, overdraftLimit, interestRateInitial, interestRateSubsequent, maxOverdraftDays)
VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);

-- Modification des informations d'un compte existant
UPDATE Account
SET clientLastName = ?, clientFirstName = ?, clientDateOfBirth = ?, monthlyNetSalary = ?, modificationDate = ?
WHERE accountID = ?;

-- F2 : Retrait d'argent
CREATE PROCEDURE WithdrawMoney
    @accountID INT,
    @amount DECIMAL,
    @transactionDateTime DATETIME
AS
BEGIN
DECLARE @currentBalance DECIMAL;
SELECT @currentBalance = principalBalance
FROM Balance
WHERE accountID = @accountID;

IF @currentBalance >= @amount
    BEGIN
        -- Effectuer le retrait
INSERT INTO Transaction (transactionDateTime, amount, transactionType, accountID)
VALUES (@transactionDateTime, -@amount, 'Withdrawal', @accountID);

-- Mettre à jour le solde principal
UPDATE Balance
SET principalBalance = principalBalance - @amount
WHERE accountID = @accountID;

SELECT 'Retrait effectué avec succès' AS Message;
END
    ELSE
BEGIN
SELECT 'Solde insuffisant pour effectuer le retrait' AS Message;
END
END;


-- F3 Consultation du solde principal d'un compte
CREATE OR REPLACE FUNCTION get_account_balance(account_id INT, date DATE)
    RETURNS DECIMAL AS $$
DECLARE
    principal_balance DECIMAL;
BEGIN
    SELECT principalBalance INTO principal_balance
    FROM Balance
    WHERE accountID = account_id AND transactionDateTime <= date
    ORDER BY transactionDateTime DESC
    LIMIT 1;

    RETURN principal_balance;
END;
$$ LANGUAGE plpgsql;

-- F4 Approvisionnement du solde via virement entrant depuis une banque externe
CREATE OR REPLACE PROCEDURE IncomingTransfer
(IN p_accountID INT, IN p_amount DECIMAL, IN p_transferDateTime TIMESTAMP)
    LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO Transaction (transactionDateTime, amount, transactionType, accountID)
    VALUES (p_transferDateTime, p_amount, 'Incoming Transfer', p_accountID);

    UPDATE Balance
    SET principalBalance = principalBalance + p_amount
    WHERE accountID = p_accountID;

    RAISE NOTICE 'Approvisionnement du solde via virement entrant effectué avec succès';
END;
$$;

-- F5 : Virement entre comptes de la même banque
CREATE PROCEDURE InternalTransfer
    @sourceAccountID INT,
    @destinationAccountID INT,
    @amount DECIMAL,
    @transferDateTime DATETIME
AS
BEGIN
DECLARE @currentBalance DECIMAL;
SELECT @currentBalance = principalBalance
FROM Balance
WHERE accountID = @sourceAccountID;

IF @currentBalance >= @amount
    BEGIN
INSERT INTO Transaction (transactionDateTime, amount, transactionType, accountID)
VALUES (@transferDateTime, -@amount, 'Outgoing Transfer', @sourceAccountID);

INSERT INTO Transaction (transactionDateTime, amount, transactionType, accountID)
VALUES (@transferDateTime, @amount, 'Incoming Transfer', @destinationAccountID);

UPDATE Balance
SET principalBalance = principalBalance - @amount
WHERE accountID = @sourceAccountID;

UPDATE Balance
SET principalBalance = principalBalance + @amount
WHERE accountID = @destinationAccountID;

SELECT 'Virement effectué avec succès' AS Message;
END
    ELSE
BEGIN
SELECT 'Solde insuffisant pour effectuer le virement' AS Message;
END
END;


-- F6 : Relevé de compte pour une période donnée
SELECT transactionDateTime, reference, transactionType, amount, principalBalance
FROM Statement
WHERE accountID = ? AND transactionDateTime BETWEEN ? AND ?
ORDER BY transactionDateTime DESC;
