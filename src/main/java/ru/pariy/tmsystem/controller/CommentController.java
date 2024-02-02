package ru.pariy.tmsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.pariy.tmsystem.dto.CommentDTO;
import ru.pariy.tmsystem.model.User;
import ru.pariy.tmsystem.service.CommentService;

@RestController
@RequestMapping("/tasks/comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{taskId}")
    public ResponseEntity<String> addComment(@PathVariable long taskId, @RequestBody CommentDTO commentDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(commentService.addComment(commentDTO, user.getEmail(), taskId),
                HttpStatus.CREATED);
    }
}
