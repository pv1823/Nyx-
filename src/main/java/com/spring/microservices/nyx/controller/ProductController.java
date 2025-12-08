package com.spring.microservices.nyx.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("/")
    public String greet() {
        return "Hello There!!!  Welcome to Nyx!!";
    }

}