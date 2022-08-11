package team.kucing.anabulshopcare.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.kucing.anabulshopcare.dto.request.CategoryRequest;
import team.kucing.anabulshopcare.entity.Category;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.repository.CategoryRepository;
import team.kucing.anabulshopcare.service.CategoryService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(CategoryRequest category) {
        Category newCategory = new Category();
        newCategory.setCategoryName(category.getName());

        return this.categoryRepository.save(newCategory);
    }

    @Override
    public Category updateCategory(CategoryRequest category, Long id) {
        Category findCategory = this.findCategory(id);

        Category updateCategory = new Category();
        updateCategory.setCategoryId(findCategory.getCategoryId());
        updateCategory.setCategoryName(category.getName());

        if (updateCategory.getIsDeleted() == null){
            updateCategory.setIsDeleted(Boolean.FALSE);
        }
        return this.categoryRepository.save(updateCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category findCategory = this.findCategory(id);
        this.categoryRepository.delete(findCategory);
    }

    private Category findCategory(Long id) {
        Optional<Category> optionalCategory = this.categoryRepository.findById(id);

        if (optionalCategory.isEmpty()){
            throw new ResourceNotFoundException("Category is not found");
        }

        return optionalCategory.get();
    }
}