package ru.pariy.tmsystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pariy.tmsystem.dto.CommentDTO;
import ru.pariy.tmsystem.dto.CommentMapper;
import ru.pariy.tmsystem.repository.CommentRepository;
import ru.pariy.tmsystem.util.TaskNotFoundException;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final static Logger logger = LoggerFactory.getLogger(CommentService.class);
    @Autowired
    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    public String addComment(CommentDTO commentDTO, String commentatorEmail, long taskId) {
        boolean commentAdded = commentRepository.addComment(commentMapper.addCommentMapper(commentDTO),
                commentatorEmail, taskId);
        if (commentAdded) {
            logger.info("New comment added by {} for task with task_id={}", commentatorEmail, taskId);
            return "Comment successfully added";
        }
        logger.info("The comment for task with task_id={} has not been added", taskId);
        throw new TaskNotFoundException("Task with id=" + taskId + " not found");
    }
}
