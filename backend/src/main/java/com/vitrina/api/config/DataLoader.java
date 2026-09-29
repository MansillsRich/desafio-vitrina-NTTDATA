package com.vitrina.api.config;

import com.vitrina.api.model.Product;
import com.vitrina.api.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        ClassPathResource resource = new ClassPathResource("catalog.csv");
        if (!resource.exists()) {
            System.out.println("⚠️ ATENCIÓN: No se encontró el archivo catalog.csv en src/main/resources/");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            boolean isFirstLine = true;
            String header = null;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    header = line;
                    isFirstLine = false;
                    continue;
                }

                if (line.trim().isEmpty()) continue;

                // Separar por comas, ignorando las que están dentro de comillas dobles
                String[] columns = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                
                if (columns.length >= 6) {
                    Product product = new Product();
                    // Eliminar comillas dobles residuales de los extremos
                    product.setId(cleanString(columns[0]));
                    product.setName(cleanString(columns[1]));
                    product.setDescription(cleanString(columns[2]));
                    product.setFormat(cleanString(columns[3]));
                    product.setCategory(cleanString(columns[4]));
                    
                    try {
                        String priceStr = cleanString(columns[5]).replaceAll("[^0-9.]", "");
                        product.setPrice(Double.parseDouble(priceStr));
                    } catch (Exception e) {
                        product.setPrice(0.0);
                    }

                    if (columns.length > 6) {
                        product.setImage(cleanString(columns[6]));
                    }

                    productRepository.save(product);
                }
            }
            System.out.println("✅ Catálogo CSV cargado con éxito. Total registros: " + productRepository.count());
        } catch (Exception e) {
            System.err.println("❌ Error al cargar catalog.csv: " + e.getMessage());
        }
    }

    private String cleanString(String input) {
        if (input == null) return "";
        return input.trim().replaceAll("^\"|\"$", "");
    }
}