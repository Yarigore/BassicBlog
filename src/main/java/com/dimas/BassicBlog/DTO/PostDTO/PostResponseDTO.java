package com.dimas.BassicBlog.DTO.PostDTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private String authorName;
    private String categoryName;
    private List<String> tagNames;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

