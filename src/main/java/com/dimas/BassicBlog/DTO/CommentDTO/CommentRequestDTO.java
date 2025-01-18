package com.dimas.BassicBlog.DTO.CommentDTO;

import lombok.Data;

@Data
public class CommentRequestDTO {
    private String content;
    private Long authorId;
    private Long postId;
}
