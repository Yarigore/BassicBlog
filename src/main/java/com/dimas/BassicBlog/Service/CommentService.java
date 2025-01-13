package com.dimas.BassicBlog.Service;

import com.dimas.BassicBlog.Entity.Comment;
import com.dimas.BassicBlog.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Optional<List<Comment>> getComments(){
        return Optional.of(commentRepository.findAll());
    }

    public Optional<Comment> getCommentById(Long id){
        return commentRepository.findById(id);
    }

    public Optional<Comment> saveComment(Comment comment){
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
