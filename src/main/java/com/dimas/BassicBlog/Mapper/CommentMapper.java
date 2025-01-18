package com.dimas.BassicBlog.Mapper;

import com.dimas.BassicBlog.DTO.CommentDTO.CommentRequestDTO;
import com.dimas.BassicBlog.DTO.CommentDTO.CommentResponseDTO;
import com.dimas.BassicBlog.Entity.Comment;
import com.dimas.BassicBlog.Entity.Post;
import com.dimas.BassicBlog.Entity.Users;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentResponseDTO toResponseDTO(Comment comment) {
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        dto.setAuthorName(comment.getAuthor().getName());
        dto.setPostId(comment.getPost().getId());
        return dto;
    }

    public Comment toEntity(CommentRequestDTO dto, Users author, Post post) {
        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setAuthor(author);
        comment.setPost(post);
        return comment;
    }
}
