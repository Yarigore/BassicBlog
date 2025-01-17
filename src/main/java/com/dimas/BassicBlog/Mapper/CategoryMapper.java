package com.dimas.BassicBlog.Mapper;

import com.dimas.BassicBlog.DTO.CategoryDTO.CategoryRequestDTO;
import com.dimas.BassicBlog.DTO.CategoryDTO.CategoryResponseDTO;
import com.dimas.BassicBlog.Entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponseDTO toResponseDTO(Category category){
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(category.getId());
        categoryResponseDTO.setName(category.getName());
        return categoryResponseDTO;
    }

    public Category toEntity(CategoryRequestDTO categoryRequestDTO){
        Category category = new Category();
        category.setName(categoryRequestDTO.getName());
        return category;
    }

}
