package com.bank.implementation.Service;

import com.bank.implementation.Model.Category;
import com.bank.implementation.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class TransactionCategoryService {
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() throws SQLException {
        return categoryRepository.findAll();
    }
    public void saveCategory(Category category) throws SQLException {
        categoryRepository.save(category);
    }
}
