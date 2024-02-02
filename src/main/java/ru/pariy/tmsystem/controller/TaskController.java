package ru.pariy.tmsystem.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.pariy.tmsystem.dto.TaskDTO;
import ru.pariy.tmsystem.dto.TaskFilterDTO;
import ru.pariy.tmsystem.model.TaskStatus;
import ru.pariy.tmsystem.model.User;
import ru.pariy.tmsystem.service.TaskService;
import ru.pariy.tmsystem.util.TaskNotCreatedException;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public void create(@RequestBody @Valid TaskDTO taskDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new TaskNotCreatedException(errorMsg.toString());
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        taskService.saveTask(taskDTO, user.getEmail());
    }

    @GetMapping()
    public List<TaskDTO> getOwnTasks() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return taskService.getOwnTasks(user.getEmail());
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<String> update(@RequestBody TaskDTO taskDTO, @PathVariable long taskId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(taskService.updateTask(taskDTO, user.getEmail(), taskId), HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> delete(@PathVariable long taskId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(taskService.deleteTask(user.getEmail(), taskId),
                HttpStatus.OK);
    }

    @PutMapping("/status/{taskId}/{status}")
    public ResponseEntity<String> changeStatus(@PathVariable long taskId, @PathVariable TaskStatus status) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(taskService.changeTaskStatus(user.getEmail(), taskId, status),
                HttpStatus.CREATED);
    }

    @PutMapping("/assign/{taskId}/{performerEmail}")
    public ResponseEntity<String> assignPerformer(@PathVariable long taskId,
                                                  @PathVariable String performerEmail) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(taskService.assignNewPerformer(user.getEmail(), performerEmail, taskId)
                , HttpStatus.CREATED);
    }

    @GetMapping("/other")
    public List<TaskDTO> getOther() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return taskService.getOtherUsersTasks(user.getEmail());
    }

    @PutMapping("/performer/{taskId}/{status}")
    public ResponseEntity<String> changeStatusPerformerTask(@PathVariable long taskId,
                                                            @PathVariable TaskStatus status) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(taskService.changePerformerTaskStatus(user.getEmail(), status, taskId),
                HttpStatus.CREATED);
    }

    @GetMapping("/author/{email}")
    public List<TaskDTO> getTasksSpecificAuthorWithComments(@PathVariable String email,
                                                            @RequestParam(defaultValue = "1") int page,
                                                            @RequestParam(defaultValue = "3") int size) {
        return taskService.getTasksSpecificAuthorWithComments(email, page, size);
    }

    @GetMapping("/performer/{email}")
    public List<TaskDTO> getTasksSpecificPerformerWithComments(@PathVariable String email,
                                                               @RequestParam(defaultValue = "1") int page,
                                                               @RequestParam(defaultValue = "3") int size) {
        return taskService.getTasksSpecificPerformerWithComments(email, page, size);
    }

    @GetMapping("/search")
    public List<TaskDTO> searchTasks(@RequestBody TaskFilterDTO taskFilterDTO,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "3") int size) {
        return taskService.searchTasks(taskFilterDTO, page, size);
    }

}
