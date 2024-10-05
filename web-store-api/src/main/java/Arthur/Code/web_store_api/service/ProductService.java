package Arthur.Code.web_store_api.service;

import Arthur.Code.web_store_api.data.CategoryRepository;
import Arthur.Code.web_store_api.data.ProductRepository;
import Arthur.Code.web_store_api.model.Product;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getAllProductsByCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new IllegalStateException("Category with ID " + categoryId + " does not exist.");
        }
        return productRepository.findByCategoryId(categoryId);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalStateException("Product not found."));
    }

    public Product getProductByName(String productName) {
        return productRepository.findProductByName(productName).orElseThrow(() -> new IllegalStateException("Product not found."));
    }

    public void createProduct(Product product) {
        product.setCreatedAt(LocalDateTime.now());
        productRepository.save(product);
    }

    public void updateProduct(Product updatedProduct) {
        productRepository.findById(updatedProduct.getId())
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setDescription(updatedProduct.getDescription());
                    product.setPrice(updatedProduct.getPrice());
                    product.setCategory(updatedProduct.getCategory());
                    product.setUpdatedAt(LocalDateTime.now());

                    return productRepository.save(product);
                })
                .orElseThrow(() -> new IllegalStateException("Product with ID " + updatedProduct.getId() + " does not exist."));
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        productRepository.deleteById(id);
    }
}
