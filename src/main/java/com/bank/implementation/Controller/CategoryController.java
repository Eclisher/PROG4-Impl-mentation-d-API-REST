package com.bank.implementation.Controller;

import com.bank.implementation.Model.Category;
import com.bank.implementation.Service.TransactionCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
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
}
