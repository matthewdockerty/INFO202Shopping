"use strict";

var module = angular.module('ShoppingApp', ['ngResource', 'ngStorage']);

module.factory('productDAO', function ($resource) {
    return $resource('/api/products/:id');
});

module.controller('ProductController', function (productDAO) {
    // load the products
    this.products = productDAO.query();
});

