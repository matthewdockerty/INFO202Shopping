/**
 * Author:  docma436
 * Created: 4/08/2018
 */

CREATE TABLE Product (
    ProductID VARCHAR(6) NOT NULL,
    Name VARCHAR(32) NOT NULL,
    Description VARCHAR(255) NOT NULL,
    Category VARCHAR(32) NOT NULL,
    ListPrice Decimal(19, 2) NOT NULL,
    QuantityInStock Integer NOT NULL,
    
    CONSTRAINT Product_PK PRIMARY KEY (ProductID)
);