package com.dimas.BassicBlog.Repository;

import com.dimas.BassicBlog.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
