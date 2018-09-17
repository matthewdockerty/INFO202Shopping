"use strict";
class SaleItem {

    constructor(product, quantity) {
        // only set the fields if we have a valid product
        if (product) {
            this.product = product;
            this.quantityPurchased = quantity;
            this.salePrice = product.listPrice;
        }
    }

    getItemTotal() {
        return this.salePrice * this.quantityPurchased;
    }

}

class ShoppingCart {

    constructor() {
        this.items = new Array();
    }

    reconstruct(sessionData) {
        for (let item of sessionData.items) {
            this.addItem(Object.assign(new SaleItem(), item));
        }
    }

    getItems() {
        return this.items;
    }

    addItem(item) {
        this.items.push(item);
    }

    setCustomer(customer) {
        this.customer = customer;
    }

    getTotal() {
        let total = 0;
        for (let item of this.items) {
            total += item.getItemTotal();
        }
        return total;
    }

}

var module = angular.module('ShoppingApp', ['ngResource', 'ngStorage']);

module.factory('productDAO', function ($resource) {
    return $resource('/api/products/:id');
});

module.factory('categoryDAO', function ($resource) {
    return $resource('/api/categories/:category');
});

module.factory('registerDAO', function ($resource) {
    return $resource('/api/register/');
});

// TODO: Implement proper credential validation!
module.factory('signInDAO', function ($resource) {
    return $resource('/api/customers/:username');
});

module.factory('cart', function ($sessionStorage) {
    let cart = new ShoppingCart();

    // is the cart in the session storage?
    if ($sessionStorage.cart) {

        // reconstruct the cart from the session data
        cart.reconstruct($sessionStorage.cart);
    }

    return cart;
});

module.controller('ProductController', function (productDAO, categoryDAO) {
    // load the products
    this.products = productDAO.query();
    // load the categories
    this.categories = categoryDAO.query();

    // click handler for the category filter buttons
    this.selectCategory = function (selectedCat) {
        this.products = categoryDAO.query({"category": selectedCat});
    };

    this.selectAll = function () {
        this.products = productDAO.query();
    };
});

module.controller('CustomerController', function (registerDAO, signInDAO, $sessionStorage, $window) {
    this.signInMessage = "Please sign in to continue.";
    this.signedIn = false;

    this.registerCustomer = function (customer) {
        registerDAO.save(null, customer);
        // TODO: Show status of account creation and appropriately redirect!
    };

    // alias 'this' so that we can access it inside callback functions
    let ctrl = this;

    this.signIn = function (username, password) {
        // get customer from web service
        signInDAO.get({'username': username},
                // success
                        function (customer) {
                            // also store the retrieved customer
                            $sessionStorage.customer = customer;
                            // redirect to home
                            $window.location.href = '.';
                        },
                        // fail
                                function () {
                                    ctrl.signInMessage = 'Sign in failed. Please try again.';
                                }
                        );
                    };

            this.checkSignIn = function () {
                if ($sessionStorage.customer) {
                    ctrl.signedIn = true;
                    ctrl.welcome = "Welcome " + $sessionStorage.customer.firstName + " " + $sessionStorage.customer.surname;
                }
            };

            this.signOut = function () {
                $sessionStorage.$reset();
                ctrl.signedIn = false;
                $window.location.href = '.';
            };
        });

module.controller('CartController', function (cart, $sessionStorage, $window) {
    this.items = cart.getItems();
    this.total = cart.getTotal();
    this.selectedProduct = $sessionStorage.selectedProduct;
    
    this.buyProduct = function (product) {
        $sessionStorage.selectedProduct = product;
        $window.location.href = '/buy.html';
    };
    
    this.addToCart = function (quantity) {
        if (quantity <= 0 || !Number.isInteger(quantity)) {
            this.addMessage = "Quantity to buy must be a postitive integer.";
            return;
        }
        
        if (quantity > this.selectedProduct.quantityInStock) {
            this.addMessage = "Only " + this.selectedProduct.quantityInStock + " products in stock.";
            return;
        }
        
        // TODO: Only allow buying available. Check this server-side!
        
        let saleItem = new SaleItem(this.selectedProduct, quantity);
        cart.addItem(saleItem);
        $sessionStorage.cart = cart;
        $window.location.href = '/products.html';
    };

});

