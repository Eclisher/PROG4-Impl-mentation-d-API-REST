package com.bank.implementation.Repository;

import com.bank.implementation.Model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.text.html.parser.DTDConstants.ID;

public class CategoryRepository {
    private static final String Id = "id";
    private static  final String NAME = "name";

    private Connection connection;

    public CategoryRepository(Connection connection) {
        this.connection = connection;
    }

    public Category save(Category category) throws SQLException {
        String query = "INSERT INTO category (name) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                category.setId(generatedKeys.getLong(1));
                return category;
            } else {
                throw new SQLException("Failed to get generated category ID.");
            }
        }
    }


    public List<Category> findAll() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM category";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getLong(Id));
                category.setName(resultSet.getString(NAME));
                categories.add(category);
            }
        }
        return categories;
    }
    public Category findById(Long id) throws SQLException {
        String query = "SELECT * FROM category WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getLong(Id));
                category.setName(resultSet.getString(NAME));
                return category;
            }
        }
        return null;
    }
    public void deleteById(Long id) throws SQLException {
        String query = "DELETE FROM category WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }
    public void updateCategory(Long categoryId, Category category) throws SQLException {
        String query = "UPDATE category SET name = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.setLong(2, categoryId);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Failed to update category. Category with ID " + categoryId + " not found.");
            }
        }
    }

}
