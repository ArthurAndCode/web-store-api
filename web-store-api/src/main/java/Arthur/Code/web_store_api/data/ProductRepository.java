package Arthur.Code.web_store_api.data;

import Arthur.Code.web_store_api.model.Address;
import Arthur.Code.web_store_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
    Optional<Product> findProductByName(String name);
}

