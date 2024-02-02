package ru.pariy.tmsystem.dto;

import ru.pariy.tmsystem.model.TaskPriority;
import ru.pariy.tmsystem.model.TaskStatus;

public class TaskFilterDTO {
    private String title;
    private String authorEmail;
    private String performerEmail;
    private TaskStatus taskStatus;
    private TaskPriority taskPriority;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public TaskPriority getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
    }
}
