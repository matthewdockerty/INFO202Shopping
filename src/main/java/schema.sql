/**
 * Author:  docma436
 * Created: 4/08/2018
 */

CREATE TABLE Product (
    Product_ID VARCHAR(6) NOT NULL,
    Name VARCHAR(32) NOT NULL CHECK (LENGTH(Name) >= 2),
    Description VARCHAR(1024) NOT NULL CHECK (LENGTH(Description) >= 4),
    Category VARCHAR(32) NOT NULL CHECK (LENGTH(Category) > 0),
    List_Price Decimal(19, 2) NOT NULL CHECK (List_Price >= 0),
    Quantity_In_Stock Integer NOT NULL CHECK (Quantity_In_Stock >= 0),
    Total_Sold INTEGER DEFAULT 0,
    
    CONSTRAINT Product_PK PRIMARY KEY (Product_ID),
    CONSTRAINT Product_ID CHECK (REGEXP_LIKE(Product_ID, '[A-Z]{2}[0-9]{4}'))
);

CREATE TABLE Product_Image (
    Product_ID VARCHAR(6) NOT NULL UNIQUE,
    Image BLOB NOT NULL,

    CONSTRAINT Image_PK PRIMARY KEY (Product_ID),
    CONSTRAINT Image_Product_ID FOREIGN KEY (Product_ID) REFERENCES Product
);


CREATE TABLE Customer (
    Person_ID INT NOT NULL AUTO_INCREMENT,
    Username VARCHAR(32) NOT NULL UNIQUE CHECK (LENGTH(Username) >= 2),
    First_Name VARCHAR(64) NOT NULL,
    Surname VARCHAR(64) NOT NULL,
    Password VARCHAR(64) NOT NULL CHECK (LENGTH(Password) >= 6),
    -- TODO: Store password hash & salt!
    Email_Address VARCHAR(254) NOT NULL UNIQUE,
    Shipping_Address VARCHAR(256) NOT NULL CHECK (LENGTH(Shipping_Address) > 16),
    Credit_Card_Details VARCHAR(128) NOT NULL CHECK (LENGTH(Credit_Card_Details) >= 16),

    CONSTRAINT Customer_PK PRIMARY KEY (Person_ID),
    CONSTRAINT Email_Address CHECK (REGEXP_LIKE(Email_Address, '[A-Za-z0-9._%+-]+@([A-Za-z0-9]+\.)*[A-Za-z0-9]+'))
);

CREATE TABLE Sale (
    Sale_ID INT NOT NULL AUTO_INCREMENT,
    Date TIMESTAMP NOT NULL,
    Status CHAR(1) NOT NULL CHECK (Status = 'P' OR STATUS = 'S'),
    Person_ID INT NOT NULL,

    CONSTRAINT Sale_PK PRIMARY KEY (Sale_ID),
    CONSTRAINT Sale_Customer FOREIGN KEY (Person_ID) REFERENCES Customer,
);

CREATE TABLE Sale_Item (
    Quantity_Purchased INT NOT NULL CHECK (Quantity_Purchased > 0),
    Sale_Price Decimal(19, 2) NOT NULL CHECK (Sale_Price >= 0),
    Product_ID VARCHAR(6) NOT NULL,
    Sale_ID INT NOT NULL,
    
    CONSTRAINT Sale_Item_PK PRIMARY KEY (Sale_ID, Product_ID, Sale_Price),
    CONSTRAINT Sale_Item_Sale_ID FOREIGN KEY (Sale_ID) REFERENCES Sale,
    CONSTRAINT Sale_Item_Product_ID FOREIGN KEY (Product_ID) REFERENCES Product
);