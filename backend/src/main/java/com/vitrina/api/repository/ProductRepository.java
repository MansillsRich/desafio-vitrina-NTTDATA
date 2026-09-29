package com.vitrina.api.repository;

import com.vitrina.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    
    // Método para buscar por nombre (ignorando mayúsculas) y filtrar por categoría y formato
    @Query("SELECT p FROM Product p WHERE " +
           "(:q IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :q, '%'))) AND " +
           "(:category IS NULL OR p.category = :category) AND " +
           "(:format IS NULL OR p.format = :format)")
    Page<Product> searchAndFilter(String q, String category, String format, Pageable pageable);

    // Métodos para obtener listas únicas de categorías y formatos para los selectores del frontend
    @Query("SELECT DISTINCT p.category FROM Product p WHERE p.category IS NOT NULL")
    List<String> findDistinctCategories();

    @Query("SELECT DISTINCT p.format FROM Product p WHERE p.format IS NOT NULL")
    List<String> findDistinctFormats();
}