package com.dimas.BassicBlog.Controller;

import com.dimas.BassicBlog.DTO.CommentDTO.CommentRequestDTO;
import com.dimas.BassicBlog.DTO.CommentDTO.CommentResponseDTO;
import com.dimas.BassicBlog.Entity.Comment;
import com.dimas.BassicBlog.Entity.Post;
import com.dimas.BassicBlog.Entity.Users;
import com.dimas.BassicBlog.Mapper.CommentMapper;
import com.dimas.BassicBlog.Service.CommentService;
import com.dimas.BassicBlog.Service.PostService;
import com.dimas.BassicBlog.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/comment")
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;
    private UserService userService;
    private PostService postService;
    private CommentMapper commentMapper;

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id)
                .map(commentMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDTO>> getComments() {
        return commentService.getComments()
                .map(comments -> {
                    List<CommentResponseDTO> dtos = comments.stream()
                            .map(commentMapper::toResponseDTO)
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(dtos);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CommentResponseDTO> createComment(@RequestBody CommentRequestDTO commentRequestDTO) {
        Optional<Users> author = userService.getUserById(commentRequestDTO.getAuthorId());
        Optional<Post> post = postService.getPostById(commentRequestDTO.getPostId());

        if (author.isPresent() && post.isPresent()) {
            Comment comment = commentMapper.toEntity(commentRequestDTO, author.get(), post.get());
            return commentService.saveComment(comment)
                    .map(commentMapper::toResponseDTO)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.badRequest().build());
        }

        return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable Long id, @RequestBody CommentRequestDTO commentRequestDTO) {
        Optional<Comment> existingComment = commentService.getCommentById(id);

        if (existingComment.isPresent()) {
            Comment commentToUpdate = existingComment.get();

            if (commentRequestDTO.getContent() != null) {
                commentToUpdate.setContent(commentRequestDTO.getContent());
            }

            return commentService.saveComment(commentToUpdate)
                    .map(commentMapper::toResponseDTO)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.badRequest().build());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        boolean isDeleted = commentService.deleteComment(id);
        if (isDeleted) return ResponseEntity.noContent().build();
        else return ResponseEntity.notFound().build();
    }
}