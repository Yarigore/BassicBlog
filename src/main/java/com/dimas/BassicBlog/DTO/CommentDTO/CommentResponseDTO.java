package com.dimas.BassicBlog.DTO.CommentDTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String authorName;
    private Long postId;
}
