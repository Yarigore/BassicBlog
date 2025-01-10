package com.dimas.BassicBlog.Service;

import com.dimas.BassicBlog.Entity.Post;
import com.dimas.BassicBlog.Repository.CategoryRepository;
import com.dimas.BassicBlog.Repository.PostRepository;
import com.dimas.BassicBlog.Repository.TagRepository;
import com.dimas.BassicBlog.Repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    private ImgbbService imgbbService;

    public Optional<List<Post>> getPosts() {
        return Optional.of(postRepository.findAll());
    }

    public Optional<Post> getPostById(Long id){
        return postRepository.findById(id);
    }

    public Optional<Post> savePost(MultipartFile file, String title, String content,
                                   Long authorId, Long categoryId, List<Long> tagIds) throws IOException {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);

        LocalDateTime now = LocalDateTime.now();
        post.setCreatedAt(now);
        post.setUpdatedAt(now);

        // Relación con el autor
        post.setAuthor(userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found")));

        // Relación con la categoría
        post.setCategory(categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found")));

        // Relación con etiquetas
        post.setTags(tagRepository.findAllById(tagIds));

        // Procesar el archivo para obtener la URL de la imagen
        if (!file.isEmpty()) {
            String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
            String response = imgbbService.uploadImage(base64Image);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);
            post.setImageUrl(jsonNode.get("data").get("url").asText());
        }

        // Asegurarse de que los comentarios estén vacíos
//        post.setComments(Collections.emptyList());

        return Optional.of(postRepository.save(post));
    }

    public Post savePost(Post post) {
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public boolean deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
