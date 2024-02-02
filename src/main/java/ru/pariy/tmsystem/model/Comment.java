package ru.pariy.tmsystem.model;

import java.time.LocalDateTime;

public class Comment {
    private long id;
    private long taskId;
    private String text;
    private String commentatorEmail;
    private LocalDateTime commentDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCommentatorEmail() {
        return commentatorEmail;
    }

    public void setCommentatorEmail(String commentatorEmail) {
        this.commentatorEmail = commentatorEmail;
    }

    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDateTime commentDate) {
        this.commentDate = commentDate;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", text='" + text + '\'' +
                ", commentatorEmail='" + commentatorEmail + '\'' +
                ", commentDate=" + commentDate +
                '}';
    }
}
