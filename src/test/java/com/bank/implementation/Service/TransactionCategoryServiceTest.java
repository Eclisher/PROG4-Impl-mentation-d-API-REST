package com.bank.implementation.Service;

import com.bank.implementation.Model.Category;
import com.bank.implementation.Model.Transaction;
import com.bank.implementation.Repository.CategoryRepository;
import com.bank.implementation.Repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TransactionCategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionCategoryService transactionCategoryService;

    @Test
    void getAllCategories_ReturnsListOfCategories() throws SQLException {
        // Arrange
        List<Category> expectedCategories = new ArrayList<>();
        expectedCategories.add(new Category(1L, "Groceries"));
        expectedCategories.add(new Category(2L, "Utilities"));

        when(categoryRepository.findAll()).thenReturn(expectedCategories);

        // Act
        List<Category> result = transactionCategoryService.getAllCategories();

        // Assert
        assertEquals(expectedCategories.size(), result.size());
        assertEquals(expectedCategories, result);
    }

    @Test
    void saveCategory_ValidCategory_SavesCategory() throws SQLException {
        // Arrange
        Category category = new Category(1L, "Groceries");

        // Act
        transactionCategoryService.saveCategory(category);

        // Assert
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void getCategoryById_ExistingId_ReturnsCategory() throws SQLException {
        // Arrange
        Long categoryId = 1L;
        Category expectedCategory = new Category(categoryId, "Groceries");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(expectedCategory));

        // Act
        Category result = transactionCategoryService.getCategoryById(categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedCategory, result);
    }

    @Test
    void getCategoryById_NonExistingId_ReturnsNull() throws SQLException {
        // Arrange
        Long categoryId = 1L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act
        Category result = transactionCategoryService.getCategoryById(categoryId);

        // Assert
        assertNull(result);
    }

    @Test
    void categorizeTransactions_TransactionsAndCategoryId_CategorizesTransactions() {
        // Arrange
        Long categoryId = 1L;
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(1L, "Groceries", BigDecimal.valueOf(50.0)));
        transactions.add(new Transaction(2L, "Utilities", BigDecimal.valueOf(100.0)));

        // Act
        transactionCategoryService.categorizeTransactions(categoryId, transactions);

        // Assert
        for (Transaction transaction : transactions) {
            assertEquals(categoryId, transaction.getCategoryId());
        }
        verify(transactionRepository, times(transactions.size())).updateTransactionCategory(anyLong(), eq(categoryId));
    }
}
