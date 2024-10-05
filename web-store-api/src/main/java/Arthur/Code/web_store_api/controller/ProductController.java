package Arthur.Code.web_store_api.controller;

import Arthur.Code.web_store_api.dto.DataResponseDTO;
import Arthur.Code.web_store_api.model.Product;
import Arthur.Code.web_store_api.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<DataResponseDTO<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        DataResponseDTO<Product> response = new DataResponseDTO<>(products, "products");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<DataResponseDTO<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        List<Product> products = productService.getAllProductsByCategory(categoryId);
        DataResponseDTO<Product> response = new DataResponseDTO<>(products, "products");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String name) {
        Product product = productService.getProductByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        updatedProduct.setId(id);
        productService.updateProduct(updatedProduct);
        return ResponseEntity.ok("Product updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully.");
    }
}

