package com.dimas.BassicBlog.DTO.PostDTO;

import lombok.Data;

import java.util.List;

@Data
public class PostRequestDTO {
    private String title;
    private String content;
    private Long authorId;
    private Long categoryId;
    private List<Long> tagIds;
}
