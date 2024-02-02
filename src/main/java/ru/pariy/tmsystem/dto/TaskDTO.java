package ru.pariy.tmsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import ru.pariy.tmsystem.model.TaskPriority;
import ru.pariy.tmsystem.model.TaskStatus;

import java.util.List;

public class TaskDTO {
    private long id;
    @NotNull(message = "Title should not be empty")
    private String title;
    private String description;
//    @NotNull(message = "Status should not be empty")
    private TaskStatus status;
    @NotNull(message = "Status should not be empty")
    private TaskPriority priority;
    @Email
    private String authorEmail;
    @Email
    @NotNull(message = "Performer email should not be empty")
    private String performerEmail;
    private List<CommentDTO> comments;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getPerformerEmail() {
        return performerEmail;
    }

    public void setPerformerEmail(String performerEmail) {
        this.performerEmail = performerEmail;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }
}
