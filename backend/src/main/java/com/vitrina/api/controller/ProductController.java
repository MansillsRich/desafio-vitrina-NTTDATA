package com.vitrina.api.controller;

import com.vitrina.api.model.Product;
import com.vitrina.api.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*") // Permite al frontend hacer peticiones
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // GET /api/products -> Para la grilla, búsqueda y paginación
    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String format,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {

        Pageable pageable = PageRequest.of(page, size);
        
        String searchQ = (q != null && !q.trim().isEmpty()) ? q.trim() : null;
        String searchCategory = (category != null && !category.trim().isEmpty()) ? category.trim() : null;
        String searchFormat = (format != null && !format.trim().isEmpty()) ? format.trim() : null;

        Page<Product> products = productRepository.searchAndFilter(searchQ, searchCategory, searchFormat, pageable);
        return ResponseEntity.ok(products);
    }

    // GET /api/products/{id} -> Para el detalle del producto (Modal)
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/products/filters -> Para llenar los `<select>` en el HTML
    @GetMapping("/filters")
    public ResponseEntity<Map<String, Object>> getFilters() {
        Map<String, Object> filters = new HashMap<>();
        filters.put("categories", productRepository.findDistinctCategories());
        filters.put("formats", productRepository.findDistinctFormats());
        return ResponseEntity.ok(filters);
    }
}