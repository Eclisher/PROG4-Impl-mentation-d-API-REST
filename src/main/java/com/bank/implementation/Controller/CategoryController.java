package com.bank.implementation.Controller;

import com.bank.implementation.Model.Category;
import com.bank.implementation.Model.Transaction;
import com.bank.implementation.Service.TransactionCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private TransactionCategoryService transactionCategoryService;

    @GetMapping("/all")
    public List<Category> getAllCategories() throws SQLException {
        return transactionCategoryService.getAllCategories();
    }
    @PostMapping("/add")
    public ResponseEntity<String> addCategory(@RequestBody Category category)  throws SQLException{
        transactionCategoryService.saveCategory(category);
        return ResponseEntity.ok("Category added successfully");
    }
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Long id, @RequestBody Category category) throws SQLException {
        Category updatedCategory = TransactionCategoryService.updateCategory(id, category);
        if (updatedCategory != null) {
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) throws SQLException {
        boolean deleted = transactionCategoryService.deleteCategory(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/{categoryId}/categorize")
    public ResponseEntity<String> categorizeTransactions(@PathVariable Long categoryId, @RequestBody List<Transaction> transactions) {
        transactionCategoryService.categorizeTransactions(categoryId, transactions);
        return ResponseEntity.ok("Transactions categorized successfully");
    }
}
