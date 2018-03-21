package com.revature.project2.comments;

import com.revature.project2.events.Event;
import com.revature.project2.events.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
@Controller
public class CommentController {
  private final CommentService commentService;
  private final EventService eventService;

  @Autowired
  public CommentController(CommentService commentService,
                           EventService eventService) {
    this.commentService = commentService;
    this.eventService = eventService;
  }

  @PostMapping("/events/{event_id}/comment")
  public Comment createComment(@PathVariable int event_id, Comment comment) {
    Event event = eventService.findByEventId(event_id).get();
    comment.setEvent(event);
    return commentService.saveComment(comment);
  }

  @GetMapping("/events/{event_id}/comment")
  public Iterable<Comment> fetchCommentbyevent(@PathVariable int event_id) {
    Event event = eventService.findByEventId(event_id).get();
    return commentService.findByEvent(event);
  }
}