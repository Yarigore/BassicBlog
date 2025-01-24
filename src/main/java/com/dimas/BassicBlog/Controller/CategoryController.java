package com.dimas.BassicBlog.Controller;

import com.dimas.BassicBlog.DTO.CategoryDTO.CategoryRequestDTO;
import com.dimas.BassicBlog.DTO.CategoryDTO.CategoryResponseDTO;
import com.dimas.BassicBlog.Entity.Category;
import com.dimas.BassicBlog.Mapper.CategoryMapper;
import com.dimas.BassicBlog.Service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/category")
@AllArgsConstructor
public class CategoryController {

    private CategoryService categoryService;
    private CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getCategory() {
        return categoryService.getCategories()
                .map(categories -> {
                    List<CategoryResponseDTO> categoryDTOs = categories.stream()
                            .map(categoryMapper::toResponseDTO)
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(categoryDTOs);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(categoryMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryMapper.toEntity(categoryRequestDTO);
        return categoryService.saveCategory(category)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PatchMapping("{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDTO categoryRequestDTO) {

        Optional<Category> categoryToChange = categoryService.getCategoryById(id);

        if (categoryToChange.isPresent()) {

            Category existingCategory = categoryToChange.get();

            if (categoryRequestDTO.getName() != null) {
                existingCategory.setName(categoryRequestDTO.getName());
            }

            Optional<Category> updateCategory = categoryService.saveCategory(existingCategory);

            return updateCategory
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.badRequest().build());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        boolean isDeleted = categoryService.deleteCategory(id);
        if (isDeleted) return ResponseEntity.noContent().build();
        else return ResponseEntity.notFound().build();
    }

}
