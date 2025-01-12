package com.dimas.BassicBlog.Service;

import com.dimas.BassicBlog.Entity.Category;
import com.dimas.BassicBlog.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Optional<Category> getCategoryById(Long id){
        return categoryRepository.findById(id);
    }

    public Optional<List<Category>> getCategories() {
        return Optional.of(categoryRepository.findAll());
    }

    public Optional<Category> saveCategory(Category category) {
        return Optional.of(categoryRepository.save(category));
    }

    public boolean deleteCategory(Long id) {
        if (categoryRepository.existsById(id)){
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
