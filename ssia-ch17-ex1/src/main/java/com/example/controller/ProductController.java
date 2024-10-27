package com.example.controller;

//import com.example.model.Product;
import com.example.entity.Product;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    // ex1)
//    @GetMapping("/sell")
//    public List<Product> sellProduct() {
//        List<Product> products = new ArrayList<>();
//
//        products.add(new Product("beer", "nikolai"));
//        products.add(new Product("candy", "nikolai"));
//        products.add(new Product("chocolate", "julien"));
//
//        return productService.sellProducts(products);
//    }

    // ex2) List.of()는 변경이 불가능한 인스턴스를 반환
//    @GetMapping("/sell")
//    public List<Product> sellProduct() {
//        List<Product> products = List.of(
//                new Product("beer", "nikolai"),
//                new Product("candy", "nikolai"),
//                new Product("chocolate", "julien"));
//
//        return productService.sellProducts(products);
//    }

    // ex3)
//    @GetMapping("/find")
//    public List<Product> findProducts() {
//        return productService.findProducts();
//    }

    // ex4)
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products/{text}")
    public List<Product> findProductsContaining(@PathVariable String text) {
        return productRepository.findProductByNameContains(text);
    }
}