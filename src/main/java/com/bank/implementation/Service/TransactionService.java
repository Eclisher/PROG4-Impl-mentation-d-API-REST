package com.bank.implementation.Service;

import com.bank.implementation.Model.Account;
import com.bank.implementation.Model.Transaction;
import com.bank.implementation.PostgresqlConnection;
import com.bank.implementation.Repository.AccountRepository;
import com.bank.implementation.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {
    private  TransactionRepository transactionRepository;
    private AccountRepository accountRepository;

    @Autowired
    public void TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }


    public void createTransaction(LocalDateTime transactionDateTime, BigDecimal amount, String transactionType, String reason, LocalDateTime effectDate, String status, Long accountId, Long categoryId) {
        transactionRepository.createTransaction(transactionDateTime, amount, transactionType, reason, effectDate, status, accountId, categoryId);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.getAllTransactions();
    }

    public List<Transaction> getTransactionsInDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.getTransactionsInDateRange(startDate, endDate);
    }

    public void createDepositTransaction(BigDecimal amount, String reason, LocalDateTime effectDate, Long accountId) {
        transactionRepository.createDepositTransaction(amount, reason, effectDate, accountId);
    }
    public Map<String, BigDecimal> getCategoryAmountsInPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.getCategoryAmountsInPeriod(startDate, endDate);
    }

    public BigDecimal getTotalExpensesInPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.getTotalExpensesInPeriod(startDate, endDate);
    }

    public BigDecimal getTotalIncomeInPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.getTotalIncomeInPeriod(startDate, endDate);
    }

    public void transferMoney(String sourceAccountNumber, String targetAccountNumber, BigDecimal amount) {
        List<Account> sourceAccounts = accountRepository.findByAccountNumber(sourceAccountNumber);
        List<Account> targetAccounts = accountRepository.findByAccountNumber(targetAccountNumber);

        if (!sourceAccounts.isEmpty() && !targetAccounts.isEmpty()) {
            Account sourceAccount = sourceAccounts.get(0); // Prendre le premier compte trouvé
            Account targetAccount = targetAccounts.get(0); // Prendre le premier compte trouvé

            Connection connection = null;
            try {
                connection = PostgresqlConnection.getConnection();
                connection.setAutoCommit(false);

                if (sourceAccount.getBalance().compareTo(amount) >= 0) {
                    // Créer la transaction de retrait du compte source
                    Transaction withdrawalTransaction = new Transaction();
                    withdrawalTransaction.setTransactionDateTime(LocalDateTime.now());
                    withdrawalTransaction.setAmount(amount.negate());
                    withdrawalTransaction.setTransactionType("debit");
                    withdrawalTransaction.setReason("Transfer to " + targetAccountNumber);
                    withdrawalTransaction.setEffectDate(LocalDateTime.now());
                    withdrawalTransaction.setStatus("completed");
                    withdrawalTransaction.setAccountId((long) sourceAccount.getAccountID());

                    // Utiliser la méthode createTransaction avec l'objet Transaction
                    transactionRepository.createTransaction(
                            withdrawalTransaction.getTransactionDateTime(),
                            withdrawalTransaction.getAmount(),
                            withdrawalTransaction.getTransactionType(),
                            withdrawalTransaction.getReason(),
                            withdrawalTransaction.getEffectDate(),
                            withdrawalTransaction.getStatus(),
                            withdrawalTransaction.getAccountId(),
                            withdrawalTransaction.getCategoryId()
                    );

                    // Mettre à jour le solde du compte source
                    sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
                    accountRepository.saveOrUpdateAccount(sourceAccount);

                    // Créer la transaction de dépôt sur le compte cible
                    Transaction depositTransaction = new Transaction();
                    depositTransaction.setTransactionDateTime(LocalDateTime.now());
                    depositTransaction.setAmount(amount);
                    depositTransaction.setTransactionType("credit");
                    depositTransaction.setReason("Transfer from " + sourceAccountNumber);
                    depositTransaction.setEffectDate(LocalDateTime.now());
                    depositTransaction.setStatus("completed");
                    depositTransaction.setAccountId((long) targetAccount.getAccountID());

                    // Utiliser la méthode createTransaction avec l'objet Transaction
                    transactionRepository.createTransaction(
                            depositTransaction.getTransactionDateTime(),
                            depositTransaction.getAmount(),
                            depositTransaction.getTransactionType(),
                            depositTransaction.getReason(),
                            depositTransaction.getEffectDate(),
                            depositTransaction.getStatus(),
                            depositTransaction.getAccountId(),
                            depositTransaction.getCategoryId()
                    );

                    // Mettre à jour le solde du compte cible
                    targetAccount.setBalance(targetAccount.getBalance().add(amount));
                    accountRepository.saveOrUpdateAccount(targetAccount);

                    // Valider la transaction
                    connection.commit();
                } else {
                    throw new IllegalArgumentException("Insufficient funds in source account");
                }
            } catch (SQLException e) {
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.setAutoCommit(true);
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Source or target account not found");
        }
    }


}
