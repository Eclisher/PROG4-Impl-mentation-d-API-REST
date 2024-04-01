package com.bank.implementation.Model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryTest {

    @Test
    public void testCategoryConstructorAndGettersSetters() {

        Category category = new Category();


        long id = 1;
        String name = "Test Category";
        category.setId(id);
        category.setName(name);


        assertEquals(id, category.getId());
        assertEquals(name, category.getName());
    }
}