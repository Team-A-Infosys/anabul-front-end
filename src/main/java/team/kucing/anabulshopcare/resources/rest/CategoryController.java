package team.kucing.anabulshopcare.resources.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.kucing.anabulshopcare.dto.request.CategoryRequest;
import team.kucing.anabulshopcare.entity.Category;
import team.kucing.anabulshopcare.service.CategoryService;

@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "03. Category Controller")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping(value = "/category/add")
    public ResponseEntity<Object> createCategory(@RequestBody CategoryRequest category){
        var newCategory = categoryService.createCategory(category);
        log.info("success create new category " + newCategory);

        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    @PutMapping("/category/{id}/update")
    public ResponseEntity<Object> updateCategory(@PathVariable(value = "id") Long id, @RequestBody CategoryRequest category){
        Category updateCategory = categoryService.updateCategory(category, id);

        return ResponseEntity.ok().body(updateCategory);

    }

    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<Object> deleteCategory(@PathVariable(value = "id") Long id){
        this.categoryService.deleteCategory(id);

        return ResponseEntity.ok().body("Success delete Category");
    }
}