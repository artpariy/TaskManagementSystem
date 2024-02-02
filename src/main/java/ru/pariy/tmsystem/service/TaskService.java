package ru.pariy.tmsystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pariy.tmsystem.dto.TaskDTO;
import ru.pariy.tmsystem.dto.TaskFilterDTO;
import ru.pariy.tmsystem.dto.TaskMapper;
import ru.pariy.tmsystem.model.Comment;
import ru.pariy.tmsystem.model.Task;
import ru.pariy.tmsystem.model.TaskStatus;
import ru.pariy.tmsystem.model.User;
import ru.pariy.tmsystem.repository.CommentRepository;
import ru.pariy.tmsystem.repository.TaskRepository;
import ru.pariy.tmsystem.repository.UserRepository;
import ru.pariy.tmsystem.util.TaskNotFoundException;
import ru.pariy.tmsystem.util.EmailNotFoundException;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final TaskMapper taskMapper;
    private final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository,
                       CommentRepository commentRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.taskMapper = taskMapper;
    }

    public void saveTask(TaskDTO task, String authorEmail) {
        taskRepository.save(taskMapper.createTaskMapper(task, authorEmail));
    }

    public List<TaskDTO> getOwnTasks(String authorEmail) {
        List<Task> taskList = taskRepository.getOwnTasks(authorEmail);
        return taskMapper.getOwnTasksMapper(taskList);
    }

    public String updateTask(TaskDTO task, String authorEmail, long taskId) {
        boolean result = taskRepository.updateTask(taskMapper.updateTaskMapper(task), authorEmail, taskId);

        if (result) {
            logger.info("Task for user='{}' with task_id={} successfully updated", authorEmail, taskId);
            return "Task successfully updated";
        } else {
            logger.info("Task for user='{}' wasn't update", authorEmail);
            throw new TaskNotFoundException("Task with id=" + taskId + " not found");
        }
    }

    public String deleteTask(String authorEmail, long id) {
        boolean deleted = taskRepository.deleteTask(authorEmail, id);

        if (deleted) {
            logger.info("Task for user='{}' with task_id={} successfully deleted", authorEmail, id);
            return "Task successfully deleted";
        } else {
            logger.info("Task for user='{}' with task_id={} wasn't deleted", authorEmail, id);
            throw new TaskNotFoundException("Task with id=" + id + " not found");
        }
    }

    public String changeTaskStatus(String authorEmail, long taskId, TaskStatus status) {
        boolean changed = taskRepository.changeTaskStatus(authorEmail, taskId, status);

        if (changed) {
            logger.info("Task status with task_id={} for user='{}' successfully updated", taskId, authorEmail);
            return "Task status successfully updated";
        } else {
            logger.info("Task status with task_id={} for user='{}' wasn't update", taskId, authorEmail);
            throw new TaskNotFoundException("Task with id=" + taskId + " not found");
        }

    }

    public String assignNewPerformer(String authorEmail, String performerEmail, long taskId) {
        User user = userRepository.getByEmail(performerEmail);

        if (user == null) {
            logger.info("Performer email='{}' not found", performerEmail);
            throw new EmailNotFoundException("Email = '" + performerEmail + "' not found");
        }

        boolean assigned = taskRepository.assignNewPerformer(authorEmail, performerEmail, taskId);
        if (assigned) {
            logger.info("Task for user='{}' with task_id={}, performer successfully assigned",
                    authorEmail, taskId);
            return "Performer successfully assigned";
        } else {
            logger.info("Task for user='{}' with task_id={}, performer wasn't assign",
                    authorEmail, taskId);
            throw new TaskNotFoundException("Task with id=" + taskId + " not found");
        }
    }

    public List<TaskDTO> getOtherUsersTasks(String authorEmail) {
        List<Task> taskList = taskRepository.getOtherUsersTasks(authorEmail);
        return taskMapper.getOtherUsersTasksMapper(taskList);
    }

    public String changePerformerTaskStatus(String performerEmail, TaskStatus status, long taskId) {
        boolean changed = taskRepository.changePerformerTaskStatus(performerEmail, status, taskId);

        if (changed) {
            logger.info("The status of the task with task_id={} performer with email='{}' has been changed",
                    taskId, performerEmail);
            return "Performer task status successfully changed";
        } else {
            logger.info("The status of the task with task_id={} performer with email='{}' wasn't changed",
                    taskId, performerEmail);
            throw new TaskNotFoundException("Performer task status with id=" + taskId + " wasn't found");
        }
    }

    public List<TaskDTO> getTasksSpecificAuthorWithComments(String email, int page, int size) {
        List<Task> taskList = taskRepository.getTasksSpecificAuthor(email, page, size);
        for (Task task : taskList) {
            List<Comment> commentList = commentRepository.getCommentsForTask(task.getId());
            task.setComments(commentList);
        }
        return taskMapper.getTasksWithCommentsMapper(taskList);
    }

    public List<TaskDTO> getTasksSpecificPerformerWithComments(String email, int page, int size) {
        List<Task> taskList = taskRepository.getTasksSpecificPerformer(email, page, size);
        for (Task task : taskList) {
            List<Comment> commentList = commentRepository.getCommentsForTask(task.getId());
            task.setComments(commentList);
        }
        return taskMapper.getTasksWithCommentsMapper(taskList);
    }

    public List<TaskDTO> searchTasks(TaskFilterDTO taskFilterDTO, int page, int size) {
        List<Task> taskList = taskRepository.searchTasks(taskMapper.searchTaskMapper(taskFilterDTO), page, size);
        return taskMapper.getTasksMapper(taskList);
    }

}
