package com.dimas.BassicBlog.Mapper;

import com.dimas.BassicBlog.DTO.Tag.TagRequestDTO;
import com.dimas.BassicBlog.DTO.Tag.TagResponseDTO;
import com.dimas.BassicBlog.Entity.Tag;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagMapper {

    public TagResponseDTO toResponseDTO(Tag tag) {
        TagResponseDTO tagDTO = new TagResponseDTO();
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());
        List<Long> postIds = tag.getPosts().stream()
                .map(post -> post.getId())
                .collect(Collectors.toList());
        tagDTO.setPostIds(postIds);
        return tagDTO;
    }

    public Tag toEntity(TagRequestDTO tagDTO) {
        Tag tag = new Tag();
        tag.setName(tagDTO.getName());
        return tag;
    }
}
