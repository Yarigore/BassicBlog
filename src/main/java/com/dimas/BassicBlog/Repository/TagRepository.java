package com.dimas.BassicBlog.Repository;

import com.dimas.BassicBlog.Entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
