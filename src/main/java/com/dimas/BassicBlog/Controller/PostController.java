package com.dimas.BassicBlog.Controller;

import com.dimas.BassicBlog.DTO.PostDTO.PostResponseDTO;
import com.dimas.BassicBlog.Entity.*;
import com.dimas.BassicBlog.Mapper.PostMapper;
import com.dimas.BassicBlog.Service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/post")
@AllArgsConstructor
public class PostController {

    public PostService postService;
    private ImgbbService imgbbService;
    private UserService userService;
    private CategoryService categoryService;
    private TagService tagService;
    private PostMapper postMapper;

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getPosts() {
        return postService.getPosts()
                .map(posts -> ResponseEntity.ok(
                        posts.stream()
                                .map(postMapper::toResponse)
                                .collect(Collectors.toList())
                ))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(post -> ResponseEntity.ok(postMapper.toResponse(post)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponseDTO> createPost(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("authorId") Long authorId,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("tagIds") List<Long> tagIds) throws IOException {

        Optional<Post> post = postService.savePost(file, title, content, authorId, categoryId, tagIds);
        return post
                .map(p -> ResponseEntity.ok(postMapper.toResponse(p)))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponseDTO> updatePost(
            @PathVariable Long id,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "authorId", required = false) Long authorId,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "tagIds", required = false) List<Long> tagIds) {

        Optional<Post> postToChange = postService.getPostById(id);

        if (postToChange.isPresent()) {
            Post existingPost = postToChange.get();

            if (title != null) existingPost.setTitle(title);
            if (content != null) existingPost.setContent(content);

            if (authorId != null) {
                userService.getUserById(authorId).ifPresent(existingPost::setAuthor);
            }
            if (categoryId != null) {
                categoryService.getCategoryById(categoryId).ifPresent(existingPost::setCategory);
            }
            if (tagIds != null) {
                List<Tag> tags = tagService.getTagsByIds(tagIds);
                existingPost.setTags(tags);
            }

            try {
                if (file != null && !file.isEmpty()) {
                    String imageUrl = imgbbService.imageToString(file);
                    existingPost.setImageUrl(imageUrl);
                }
            } catch (Exception e) {
                System.err.println("Error al subir la imagen: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            return ResponseEntity.ok(postMapper.toResponse(postService.savePost(existingPost)));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePost(@RequestParam Long id) {
        boolean isDeleted = postService.deletePost(id);
        if (isDeleted) return ResponseEntity.noContent().build();
        else return ResponseEntity.notFound().build();
    }

}
