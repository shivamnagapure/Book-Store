package com.shivam.catalogservice.web.controllers;

import com.shivam.catalogservice.domain.PageResult;
import com.shivam.catalogservice.domain.Product;
import com.shivam.catalogservice.domain.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
class ProductController {

    private final ProductService productService ;

    ProductController(ProductService productService){
        this.productService = productService ;
    }

    @GetMapping
    PageResult<Product> getProducts(@RequestParam (name = "page" , defaultValue = "1") int page){
        return productService.getProducts(page) ;
    }
}
