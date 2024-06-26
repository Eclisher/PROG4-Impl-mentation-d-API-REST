package com.bank.implementation.Service;

import com.bank.implementation.Model.Category;
import com.bank.implementation.Model.Transaction;
import com.bank.implementation.Repository.CategoryRepository;
import com.bank.implementation.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionCategoryService {
    private static CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;
    @Autowired
    public TransactionCategoryService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Category> getAllCategories() throws SQLException {
        return categoryRepository.findAll();
    }

    public void saveCategory(Category category) throws SQLException {
        categoryRepository.save(category);
    }

    public Category getCategoryById(Long id) throws SQLException {
        Optional<Category> optionalCategory = Optional.ofNullable(categoryRepository.findById(id));
        return optionalCategory.orElse(null);
    }


    public static Category updateCategory(Long id, Category category) throws SQLException {
        Optional<Category> optionalCategory = Optional.ofNullable(categoryRepository.findById(id));
        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setName(category.getName());
            return categoryRepository.save(existingCategory);
        } else {
            return null;
        }
    }

    public boolean deleteCategory(Long id) throws SQLException {
        Optional<Category> optionalCategory = Optional.ofNullable(categoryRepository.findById(id));
        if (optionalCategory.isPresent()) {
            categoryRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    public void categorizeTransactions(Long categoryId, List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            transaction.setCategoryId(categoryId);
        }
        for (Transaction transaction : transactions) {
            transactionRepository.updateTransactionCategory(transaction.getTransactionId(), categoryId);
        }
    }

}
