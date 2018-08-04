/**
 * Author:  docma436
 * Created: 4/08/2018
 */

CREATE TABLE Product (
    Product_ID VARCHAR(6) NOT NULL,
    Name VARCHAR(32) NOT NULL,
    Description VARCHAR(255) NOT NULL,
    Category VARCHAR(32) NOT NULL,
    List_Price Decimal(19, 2) NOT NULL,
    Quantity_In_Stock Integer NOT NULL,
    
    CONSTRAINT Product_PK PRIMARY KEY (Product_ID)
);