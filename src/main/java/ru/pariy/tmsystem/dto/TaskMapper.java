package ru.pariy.tmsystem.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pariy.tmsystem.model.TaskStatus;
import ru.pariy.tmsystem.model.Task;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskMapper {
    private final CommentMapper commentMapper;
    @Autowired
    public TaskMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    public Task createTaskMapper(TaskDTO taskDTO, String userEmail) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(TaskStatus.TODO);
        task.setPriority(taskDTO.getPriority());
        task.setAuthorEmail(userEmail);
        task.setPerformerEmail(taskDTO.getPerformerEmail());
        return task;
    }

    public Task updateTaskMapper(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setPriority(taskDTO.getPriority());
        task.setPerformerEmail(taskDTO.getPerformerEmail());
        return task;
    }

    public Task searchTaskMapper(TaskFilterDTO taskFilterDTO) {
        Task task = new Task();
        task.setTitle(taskFilterDTO.getTitle());
        task.setAuthorEmail(taskFilterDTO.getAuthorEmail());
        task.setPerformerEmail(taskFilterDTO.getPerformerEmail());
        task.setStatus(taskFilterDTO.getTaskStatus());
        task.setPriority(taskFilterDTO.getTaskPriority());
        return task;
    }

    public List<TaskDTO> getOwnTasksMapper(List<Task> taskList) {
        List<TaskDTO> taskDTOList = new ArrayList<>();
        TaskDTO taskDTO;
        for (Task task : taskList) {
            taskDTO = new TaskDTO();
            taskDTO.setId(task.getId());
            taskDTO.setTitle(task.getTitle());
            taskDTO.setDescription(task.getDescription());
            taskDTO.setStatus(task.getStatus());
            taskDTO.setPriority(task.getPriority());
            taskDTO.setAuthorEmail(task.getAuthorEmail());
            taskDTO.setPerformerEmail(task.getPerformerEmail());
            taskDTOList.add(taskDTO);
        }
        return taskDTOList;
    }

    public List<TaskDTO> getOtherUsersTasksMapper(List<Task> taskList) {
        List<TaskDTO> taskDTOList = new ArrayList<>();
        TaskDTO taskDTO;
        for (Task task : taskList) {
            taskDTO = new TaskDTO();
            taskDTO.setId(task.getId());
            taskDTO.setTitle(task.getTitle());
            taskDTO.setDescription(task.getDescription());
            taskDTO.setStatus(task.getStatus());
            taskDTO.setPriority(task.getPriority());
            taskDTO.setAuthorEmail(task.getAuthorEmail());
            taskDTO.setPerformerEmail(task.getPerformerEmail());
            taskDTOList.add(taskDTO);
        }
        return taskDTOList;
    }

    public List<TaskDTO> getTasksMapper(List<Task> taskList) {
        List<TaskDTO> taskDTOList = new ArrayList<>();
        TaskDTO taskDTO;
        for (Task task : taskList) {
            taskDTO = new TaskDTO();
            taskDTO.setId(task.getId());
            taskDTO.setTitle(task.getTitle());
            taskDTO.setDescription(task.getDescription());
            taskDTO.setStatus(task.getStatus());
            taskDTO.setPriority(task.getPriority());
            taskDTO.setAuthorEmail(task.getAuthorEmail());
            taskDTO.setPerformerEmail(task.getPerformerEmail());
            taskDTOList.add(taskDTO);
        }
        return taskDTOList;
    }

    public List<TaskDTO> getTasksWithCommentsMapper(List<Task> taskList) {
        List<TaskDTO> taskDTOList = new ArrayList<>();
        TaskDTO taskDTO;
        for (Task task : taskList) {
            taskDTO = new TaskDTO();
            taskDTO.setId(task.getId());
            taskDTO.setTitle(task.getTitle());
            taskDTO.setDescription(task.getDescription());
            taskDTO.setStatus(task.getStatus());
            taskDTO.setPriority(task.getPriority());
            taskDTO.setAuthorEmail(task.getAuthorEmail());
            taskDTO.setPerformerEmail(task.getPerformerEmail());
            taskDTO.setComments(commentMapper.getCommentMapper(task.getComments()));
            taskDTOList.add(taskDTO);
        }
        return taskDTOList;
    }
}
