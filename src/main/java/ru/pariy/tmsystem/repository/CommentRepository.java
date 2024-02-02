package ru.pariy.tmsystem.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.pariy.tmsystem.model.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CommentRepository {
    private final JdbcTemplate jdbcTemplate;
    private final static Logger logger = LoggerFactory.getLogger(CommentRepository.class);
    private final CommentMapper commentMapper = new CommentMapper();
    @Autowired
    public CommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Добавление комментария к определенной задаче
    public boolean addComment(Comment comment, String commentatorEmail, long taskId) {
        final String SQL_ADD_COMMENT = "INSERT INTO tasks_comments " +
                "(task_id, text, commentator_email, comment_date) VALUES(?,?,?,?)";
        return jdbcTemplate.update(SQL_ADD_COMMENT,
                taskId, comment.getText(),
                commentatorEmail, comment.getCommentDate()) > 0;
    }

    // Получение комментариев для определенной задачи
    public List<Comment> getCommentsForTask(long taskId) {
        final String SQL_GET_COMMENTS = "SELECT * FROM tasks_comments WHERE task_id=?";
        return jdbcTemplate.query(SQL_GET_COMMENTS, commentMapper, taskId);
    }

    private static class CommentMapper implements RowMapper<Comment> {
        @Override
        public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Comment comment = new Comment();
            comment.setTaskId(rs.getLong("task_id"));
            comment.setText(rs.getString("text"));
            comment.setCommentatorEmail(rs.getString("commentator_email"));
            comment.setCommentDate(rs.getTimestamp("comment_date").toLocalDateTime());
            return comment;
        }
    }
}
