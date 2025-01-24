package com.dimas.BassicBlog.Mapper;

import com.dimas.BassicBlog.DTO.PostDTO.PostRequestDTO;
import com.dimas.BassicBlog.DTO.PostDTO.PostResponseDTO;
import com.dimas.BassicBlog.Entity.Post;
import com.dimas.BassicBlog.Entity.Tag;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PostMapper {

    // Convertir de Entity a DTO
    public PostResponseDTO toResponse(Post post) {
        PostResponseDTO response = new PostResponseDTO();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        response.setImageUrl(post.getImageUrl());
        response.setAuthorName(post.getAuthor().getName());
        response.setCategoryName(post.getCategory().getName());
        response.setTagNames(post.getTags().stream().map(Tag::getName).collect(Collectors.toList()));
        response.setCreatedAt(post.getCreatedAt());
        response.setUpdatedAt(post.getUpdatedAt());
        return response;
    }

    // Convertir de DTO a Entity
    public Post toEntity(PostRequestDTO request) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        // Nota: Las relaciones deben ser configuradas por el servicio, porque el mapper no tiene acceso a los repositorios
        return post;
    }
}
