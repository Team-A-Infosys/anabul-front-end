package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import team.kucing.anabulshopcare.dto.request.CategoryRequest;
import team.kucing.anabulshopcare.entity.Category;

public interface CategoryService {
    Category createCategory(CategoryRequest category);

    Category updateCategory(CategoryRequest category, Long id);

    void deleteCategory(Long id);
}