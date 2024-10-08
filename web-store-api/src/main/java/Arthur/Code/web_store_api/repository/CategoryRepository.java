package Arthur.Code.web_store_api.repository;

import Arthur.Code.web_store_api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Object> findByName(String name);
}
