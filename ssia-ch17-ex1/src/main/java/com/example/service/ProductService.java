package com.example.service;

import com.example.model.Product;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    // ex1, ex2
//    @PreFilter("filterObject.owner == authentication.name")
//    public List<Product> sellProducts(List<Product> products) {
//        // sell products and return the sold products list
//        return products;
//    }

    // ex3
    @PostFilter("filterObject.owner == authentication.principal.username")
    public List<Product> findProducts() {
        List<Product> products = new ArrayList<>();

        products.add(new Product("beer", "nikolai"));
        products.add(new Product("candy", "nikolai"));
        products.add(new Product("chocolate", "julien"));

        return products;
    }
}