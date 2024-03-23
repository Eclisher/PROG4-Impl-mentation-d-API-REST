package com.bank.implementation.Repository;

import com.bank.implementation.Model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    private static final String Id = "id";
    private static  final String NAME = "name";

    private Connection connection;

    public CategoryRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Category category) throws SQLException {
        String query = "INSERT INTO category (name) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                category.setId(generatedKeys.getLong(1));
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
}
