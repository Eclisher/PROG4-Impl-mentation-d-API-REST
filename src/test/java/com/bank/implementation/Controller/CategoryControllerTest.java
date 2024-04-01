package com.bank.implementation.Controller;
import com.bank.implementation.Controller.CategoryController;
import com.bank.implementation.Model.Category;
import com.bank.implementation.Model.Transaction;
import com.bank.implementation.Service.TransactionCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private TransactionCategoryService transactionCategoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "Food"));
        categories.add(new Category(2L, "Entertainment"));

        when(transactionCategoryService.getAllCategories()).thenReturn(categories);

        List<Category> result = categoryController.getAllCategories();

        assertEquals(categories.size(), result.size());
        verify(transactionCategoryService, times(1)).getAllCategories();
    }

    @Test
    void testAddCategory() throws SQLException {
        Category category = new Category(1L, "Food");

        ResponseEntity<String> response = categoryController.addCategory(category);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category added successfully", response.getBody());
        verify(transactionCategoryService, times(1)).saveCategory(any(Category.class));
    }

    @Test
    void testUpdateCategory() throws SQLException {
        Long id = 1L;
        Category category = new Category(id, "Food");

        when(transactionCategoryService.updateCategory(id, category)).thenReturn(category);

        ResponseEntity<Category> response = categoryController.updateCategory(id, category);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(category, response.getBody());
    }

    @Test
    void testDeleteCategory() throws SQLException {
        Long id = 1L;

        when(transactionCategoryService.deleteCategory(id)).thenReturn(true);

        ResponseEntity<Void> response = categoryController.deleteCategory(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testCategorizeTransactions() {
        Long categoryId = 1L;
        List<Transaction> transactions = new ArrayList<>();

        ResponseEntity<String> response = categoryController.categorizeTransactions(categoryId, transactions);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Transactions categorized successfully", response.getBody());
        verify(transactionCategoryService, times(1)).categorizeTransactions(categoryId, transactions);
    }
}
