package Arthur.Code.web_store_api.service;

import Arthur.Code.web_store_api.data.CategoryRepository;
import Arthur.Code.web_store_api.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new IllegalStateException("Category not found."));
    }

    public void createCategory(Category category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new IllegalStateException(category + " is already used.");
        }
        categoryRepository.save(category);
    }

    public void updateCategory(Category updatedCategory) {
        categoryRepository.findById(updatedCategory.getId())
                .map(category -> {
                    category.setName(updatedCategory.getName());
                    category.setDescription(updatedCategory.getDescription());

                    return categoryRepository.save(category);
                })
                .orElseThrow(() -> new IllegalStateException("Category with ID " + updatedCategory.getId() + " does not exist."));

    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalStateException("Category with ID " + id + " does not exist.");
        }
        categoryRepository.deleteById(id);
    }
}
