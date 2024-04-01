CREATE TABLE Category (
    categoryID SERIAL PRIMARY KEY,
    categoryName VARCHAR(50) UNIQUE
);
INSERT INTO category (categoryname) VALUES ('mounthSalary');
INSERT INTO category (categoryname) VALUES ('gifts');
INSERT INTO category (categoryname) VALUES ('walk');
