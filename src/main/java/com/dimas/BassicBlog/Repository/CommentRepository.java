package com.dimas.BassicBlog.Repository;

import com.dimas.BassicBlog.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
