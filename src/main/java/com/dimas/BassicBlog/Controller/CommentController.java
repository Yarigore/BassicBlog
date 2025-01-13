package com.dimas.BassicBlog.Controller;

import com.dimas.BassicBlog.Entity.Comment;
import com.dimas.BassicBlog.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id){
        return commentService.getCommentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getComments(){
        return commentService.getComments()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment){
        return commentService.saveComment(comment)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment){

        Optional<Comment> existingComment = commentService.getCommentById(id);

        if (existingComment.isPresent()){

            Comment existingCommentToUpdate = existingComment.get();

            if (comment.getContent() != null){
                existingCommentToUpdate.setContent(comment.getContent());
            }
            return commentService.saveComment(existingCommentToUpdate)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.badRequest().build());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long id){
        boolean isDelete = commentService.deleteComment(id);
        if (isDelete) return ResponseEntity.noContent().build();
        else return ResponseEntity.notFound().build();
    }

}
