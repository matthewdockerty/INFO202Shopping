/**
 * Author:  docma436
 * Created: 4/08/2018
 */

CREATE TABLE Product (
    Product_ID VARCHAR(6) NOT NULL,
    Name VARCHAR(32) NOT NULL CHECK (LENGTH(Name) >= 2),
    Description VARCHAR(255) NOT NULL CHECK (LENGTH(Description) >= 4),
    Category VARCHAR(32) NOT NULL CHECK (LENGTH(Category) > 0),
    List_Price Decimal(19, 2) NOT NULL CHECK (List_Price >= 0),
    Quantity_In_Stock Integer NOT NULL CHECK (Quantity_In_Stock >= 0),
    
    CONSTRAINT Product_PK PRIMARY KEY (Product_ID),
    CONSTRAINT Product_ID CHECK(REGEXP_LIKE(Product_ID, '[A-Z]{2}[0-9]{4}'))
);


CREATE TABLE Customer (
    Person_ID Integer NOT NULL AUTO_INCREMENT,
    Username VARCHAR(32) NOT NULL,
    First_Name VARCHAR(32) NOT NULL,
    Surname VARCHAR(32) NOT NULL,
    
    # WIP

);