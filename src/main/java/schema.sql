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
    CONSTRAINT Product_ID CHECK (REGEXP_LIKE(Product_ID, '[A-Z]{2}[0-9]{4}'))
);


CREATE TABLE Customer (
    Person_ID Integer NOT NULL AUTO_INCREMENT,
    Username VARCHAR(32) NOT NULL UNIQUE CHECK (LENGTH(Username) >= 2),
    First_Name VARCHAR(64) NOT NULL,
    Surname VARCHAR(64) NOT NULL,
    Password VARCHAR(64) NOT NULL CHECK (LENGTH(Password) >= 6),
    -- TODO: Store password hash & salt!
    Email_Address VARCHAR(254) NOT NULL,
    Shipping_Address VARCHAR(256) NOT NULL CHECK (LENGTH(Shipping_Address) > 16),
    Credit_Card_Details VARCHAR(128) NOT NULL CHECK (LENGTH(Credit_Card_Details) >= 16),

    CONSTRAINT Customer_PK PRIMARY KEY (Person_ID),
    CONSTRAINT Email_Address CHECK (REGEXP_LIKE(Email_Address, '[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]+'))
);