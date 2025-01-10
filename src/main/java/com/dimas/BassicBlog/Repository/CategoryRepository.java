package com.dimas.BassicBlog.Repository;

import com.dimas.BassicBlog.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
