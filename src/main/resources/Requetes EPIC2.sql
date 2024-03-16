-- F1 Catégorisation des transactions
CREATE PROCEDURE CategorizeTransactions
    @transactionIDs VARCHAR(MAX),
    @categoryID INT
AS
BEGIN
    INSERT INTO TransactionCategory (transactionID, categoryID)
    SELECT value, @categoryID
    FROM STRING_SPLIT(@transactionIDs, ',');
END;

--  F2 Somme des montants par catégorie dans une période donnée
SELECT c.categoryName, SUM(t.amount) AS totalAmount
FROM Transaction t
INNER JOIN TransactionCategory tc ON t.transactionID = tc.transactionID
INNER JOIN Category c ON tc.categoryID = c.categoryID
WHERE t.transactionDateTime BETWEEN @startDate AND @endDate
GROUP BY c.categoryName;

-- Somme des dépenses et des rentrées d'argent dans une période donnée
SELECT CASE WHEN t.transactionType = 'Withdrawal' THEN 'Dépense' ELSE 'Entrée' END AS transactionType,
       SUM(t.amount) AS totalAmount
FROM Transaction t
WHERE t.transactionDateTime BETWEEN @startDate AND @endDate
GROUP BY t.transactionType;
