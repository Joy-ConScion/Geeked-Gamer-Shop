package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.service.CategoryService;
import org.yearup.service.ProductService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("categories")
public class CategoriesController {
    private final CategoryService categoryService;
    private final ProductService productService;


    @Autowired
    public CategoriesController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Category>> getAll() {
        List<Category> categories = categoryService.getAllCategories();
        System.out.println("Categories = " + categories);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Category> getById(@PathVariable int id) {
        Category category = categoryService.getById(id);
        // get the category by id
        if (category == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @GetMapping("{categoryId}/products")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Product>> getProductsById(@PathVariable int categoryId) {
        return ResponseEntity.ok(productService.listByCategoryId(categoryId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(category));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> updateCategory(@PathVariable int id, @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.update(id, category));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id) {
        if (categoryService.delete(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
