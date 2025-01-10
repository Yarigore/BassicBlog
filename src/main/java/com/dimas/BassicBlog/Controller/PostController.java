package com.dimas.BassicBlog.Controller;

import com.dimas.BassicBlog.Entity.*;
import com.dimas.BassicBlog.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/post")
public class PostController {

    @Autowired
    public PostService postService;
    @Autowired
    private ImgbbService imgbbService;
    @Autowired
    private UserService userService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;

    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        return postService.getPosts()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Post> createPost(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("authorId") Long authorId,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("tagIds") List<Long> tagIds) throws IOException {

        Optional<Post> post = postService.savePost(file, title, content, authorId, categoryId, tagIds);
        return post
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id,
                                           @RequestParam(value = "file", required = false) MultipartFile file,
                                           @RequestBody Post post) {

        Optional<Post> postToChange = postService.getPostById(id);

        if (postToChange.isPresent()) {
            Post existingRole = postToChange.get();
            if (post.getTitle() != null) {
                existingRole.setTitle(post.getTitle());
            }
            if (post.getContent() != null) {
                existingRole.setContent(post.getContent());
            }
            if (post.getAuthor() != null) {
                existingRole.setAuthor(post.getAuthor());
            }
            if (post.getCategory() != null) {
                existingRole.setCategory(post.getCategory());
            }
            if (post.getTags() != null) {
                existingRole.setTags(post.getTags());
            }
            return ResponseEntity.ok(postService.savePost(existingRole));
        }
        // Si no se encuentra el Post
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Post> deletePost(@RequestParam Long id) {
        boolean isDeleted = postService.deletePost(id);
        if (isDeleted) return ResponseEntity.noContent().build();
        else return ResponseEntity.notFound().build();

    }

}
