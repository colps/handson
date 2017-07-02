package com.tagrem.prizy.command;


import com.tagrem.prizy.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductList {

    private final List<Product> products = new ArrayList<>();

    // TODO Pagination

    public ProductList(List<Product> products) {
        this.products.addAll(products);
    }

    public List<Product> getProducts() {
        return products;
    }


}
