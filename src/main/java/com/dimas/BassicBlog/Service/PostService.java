package com.dimas.BassicBlog.Service;

import com.dimas.BassicBlog.Entity.Post;
import com.dimas.BassicBlog.Repository.CategoryRepository;
import com.dimas.BassicBlog.Repository.PostRepository;
import com.dimas.BassicBlog.Repository.TagRepository;
import com.dimas.BassicBlog.Repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {

    private PostRepository postRepository;

    private UserRepository userRepository;

    private CategoryRepository categoryRepository;

    private TagRepository tagRepository;

    private ImgbbService imgbbService;

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public Optional<List<Post>> getPosts() {
        return Optional.of(postRepository.findAll());
    }

    public Optional<Post> savePost(MultipartFile file, String title, String content,
                                   Long authorId, Long categoryId, List<Long> tagIds) throws IOException {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);

        post.setAuthor(userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found")));

        post.setCategory(categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found")));

        post.setTags(tagRepository.findAllById(tagIds));

        if (file != null && !file.isEmpty()) {
            post.setImageUrl(uploadImage(file));
        }

        return Optional.of(postRepository.save(post));
    }

    public Post savePost(Post post) {
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public String uploadImage(MultipartFile file) throws IOException {
        String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
        String response = imgbbService.uploadImage(base64Image);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        return jsonNode.get("data").get("url").asText();
    }

    public boolean deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
