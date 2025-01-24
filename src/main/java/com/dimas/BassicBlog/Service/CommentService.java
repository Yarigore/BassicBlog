package com.dimas.BassicBlog.Service;

import com.dimas.BassicBlog.Entity.Comment;
import com.dimas.BassicBlog.Entity.Post;
import com.dimas.BassicBlog.Entity.Users;
import com.dimas.BassicBlog.Repository.CommentRepository;
import com.dimas.BassicBlog.Repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserService userService;


    public Optional<List<Comment>> getComments(){
        return Optional.of(commentRepository.findAll());
    }

    public Optional<Comment> getCommentById(Long id){
        return commentRepository.findById(id);
    }

    public Optional<Comment> saveComment(Comment comment){
        return Optional.of(commentRepository.save(comment));
    }
    public Optional<Comment> createComment(Long postId, Long authorId, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Users users = userService.getUserById(authorId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setAuthor(users);
        comment.setPost(post);

        return Optional.of(commentRepository.save(comment));
    }

    public boolean deleteComment(Long id){
        if (commentRepository.existsById(id)){
            commentRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
