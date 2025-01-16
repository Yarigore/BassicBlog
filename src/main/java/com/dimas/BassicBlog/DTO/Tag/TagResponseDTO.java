package com.dimas.BassicBlog.DTO.Tag;

import lombok.Data;

import java.util.List;

@Data
public class TagResponseDTO {
    private Long id;
    private String name;
    private List<Long> postIds;

}
