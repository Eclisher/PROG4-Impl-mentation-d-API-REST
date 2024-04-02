package com.bank.implementation.Repository;

import static org.assertj.core.api.Assertions.*;

import com.bank.implementation.Model.Category;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CategoryRepositoryTest {

    private static Connection connection;
    private static CategoryRepository categoryRepository;

    @BeforeAll
    static void setUp() throws SQLException {
        // Establish connection with the test database
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_db", "username", "password");
        categoryRepository = new CategoryRepository(connection);
    }

    @AfterAll
    static void tearDown() throws SQLException {
        // Close the connection after tests
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    void testSaveAndFindAll() throws SQLException {
        // Create a new category
        Category category = new Category();
        category.setName("Test Category");

        // Save the category to the database
        Category savedCategory = categoryRepository.save(category);

        // Check if the category is saved successfully
        assertThat(savedCategory.getId()).isNotNull();

        // Retrieve all categories from the database
        List<Category> categories = categoryRepository.findAll();

        // Check if the list of categories contains the saved category
        assertThat(categories).contains(savedCategory);
    }

    @Test
    void testFindById() throws SQLException {
        // Create a new category
        Category category = new Category();
        category.setName("Test Category");

        // Save the category to the database
        Category savedCategory = categoryRepository.save(category);

        // Retrieve the category using its ID
        Category foundCategory = categoryRepository.findById(savedCategory.getId());

        // Check if the retrieved category matches the previously saved one
        assertThat(foundCategory).isEqualTo(savedCategory);
    }

    @Test
    void testUpdateCategory() throws SQLException {
        // Create a new category
        Category category = new Category();
        category.setName("Test Category");

        // Save the category to the database
        Category savedCategory = categoryRepository.save(category);

        // Update the category name
        String updatedName = "Updated Test Category";
        savedCategory.setName(updatedName);
        categoryRepository.updateCategory(savedCategory.getId(), savedCategory);

        // Retrieve the updated category from the database
        Category updatedCategory = categoryRepository.findById(savedCategory.getId());

        // Check if the category name is successfully updated
        assertThat(updatedCategory.getName()).isEqualTo(updatedName);
    }

    @Test
    void testDeleteById() throws SQLException {
        // Create a new category
        Category category = new Category();
        category.setName("Test Category");

        // Save the category to the database
        Category savedCategory = categoryRepository.save(category);

        // Delete the category using its ID
        categoryRepository.deleteById(savedCategory.getId());

        // Check if the category is successfully deleted by trying to find it in the database
        assertThat(categoryRepository.findById(savedCategory.getId())).isNull();
    }
}
