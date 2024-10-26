package com.example.controller;

import com.example.model.Document;
import com.example.model.Employee;
import com.example.service.DocumentService;
import com.example.service.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    @Autowired
    private NameService nameService;

    // ex1, ex6
    @GetMapping("/hello")
    public String hello() {
        return "Hello, " + nameService.getName();
    }

    // ex2
//    @GetMapping("/secret/names/{name}")
//    public List<String> names(@PathVariable String name) {
//        return nameService.getSecretNames(name);
//    }

    // ex3
//    @GetMapping("/book/details/{name}")
//    public Employee getDetails(@PathVariable String name) {
//        return nameService.getBookDetails(name);
//    }

    // ex4, ex5
    @Autowired
    private DocumentService documentService;

    @GetMapping("/documents/{code}")
    public Document getDetails(@PathVariable String code) {
        return documentService.getDocument(code);
    }
}