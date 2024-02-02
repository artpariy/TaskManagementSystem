package ru.pariy.tmsystem.dto;

import org.springframework.stereotype.Component;
import ru.pariy.tmsystem.model.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapper {
    public Comment addCommentMapper(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setTaskId(commentDTO.getTaskId());
        comment.setText(commentDTO.getText());
        comment.setCommentDate(LocalDateTime.now());

        return comment;
    }

    public List<CommentDTO> getCommentMapper(List<Comment> comments) {
        List<CommentDTO> commentDTOList = new ArrayList<>();
        CommentDTO commentDTO;
        for (Comment comment : comments) {
            commentDTO = new CommentDTO();
            commentDTO.setTaskId(comment.getTaskId());
            commentDTO.setText(comment.getText());
            commentDTO.setCommentatorEmail(comment.getCommentatorEmail());
            commentDTO.setCommentDate(comment.getCommentDate());
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }
}
