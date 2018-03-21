package com.revature.project2.comments;

import com.revature.project2.events.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    @Autowired
    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }
    public Optional<Comment> findByCommentId(int id){
        return commentRepository.findById(id);
    }
    public Iterable<Comment> findAllComments(){
        return commentRepository.findAll();
    }
    public Comment saveComment(Comment comment){
        return commentRepository.save(comment);
    }
    public Iterable<Comment> findByEvent(Event event){
        return commentRepository.findByEvent(event);
    }
}
