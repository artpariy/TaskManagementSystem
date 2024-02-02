package ru.pariy.tmsystem.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.pariy.tmsystem.model.Task;
import ru.pariy.tmsystem.model.TaskPriority;
import ru.pariy.tmsystem.model.TaskStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class TaskRepository {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private final TaskMapper taskMapper = new TaskMapper();

    // Сохранение задачи
    public void save(Task task) {
        final String SQL_CREATE_TASK = "INSERT INTO tasks " +
                "(title, description, status_id, priority_id, author_email, performer_email) VALUES(?,?,?,?,?,?)";
        jdbcTemplate.update(SQL_CREATE_TASK, task.getTitle(), task.getDescription(),
                task.getStatus().getId(), task.getPriority().getId(),
                task.getAuthorEmail(), task.getPerformerEmail());

    }

    // Просмотр собственных задач
    public List<Task> getOwnTasks(String authorEmail) {
        final String SQL_GET_TASK = "SELECT * " +
                " FROM tasks WHERE author_email=? ORDER BY id";
        return jdbcTemplate.query(SQL_GET_TASK, taskMapper, authorEmail);
    }

    // Обновление задачи
    public boolean updateTask(Task task, String authorEmail, long taskId) {
        final String SQL_UPDATE_TASK = "UPDATE tasks SET title=?, description=?," +
                " status_id=?, priority_id=?, performer_email=? WHERE author_email=? AND id=?";
        int row = jdbcTemplate.update(SQL_UPDATE_TASK,
                task.getTitle(), task.getDescription(), task.getStatus().getId(),
                task.getPriority().getId(), task.getPerformerEmail(), authorEmail, taskId);
        return row == 1;
    }

    // Удаление задачи
    public boolean deleteTask(String authorEmail, long taskId) {
        final String SQL_DELETE_TASK = "DELETE FROM tasks WHERE author_email=? AND id=?";
        int row = jdbcTemplate.update(SQL_DELETE_TASK, authorEmail, taskId);
        return row == 1;
    }

    // Изменение статуса
    public boolean changeTaskStatus(String authorEmail, long taskId, TaskStatus taskStatus) {
        final String SQL_UPDATE_STATUS = "UPDATE tasks SET status_id=? WHERE author_email=? AND id=?";
        int row = jdbcTemplate.update(SQL_UPDATE_STATUS, taskStatus.getId(), authorEmail, taskId);
        return row == 1;
    }

    // Назначение нового исполнителя
    public boolean assignNewPerformer(String authorEmail, String performerEmail, long taskId) {
        final String SQL_UPDATE_PERFORMER = "UPDATE tasks SET performer_email=? WHERE author_email=? AND id=?";
        int row = jdbcTemplate.update(SQL_UPDATE_PERFORMER, performerEmail, authorEmail, taskId);
        return row == 1;

    }

    // --------------------------------------- 4 пункт
    // Получение задач остальных авторов
    public List<Task> getOtherUsersTasks(String authorEmail) {
        final String SQL_GET_OTHER_USERS_TASKS = "SELECT * FROM tasks " +
                "WHERE author_email !=? ORDER BY id";
        return jdbcTemplate.query(SQL_GET_OTHER_USERS_TASKS, taskMapper, authorEmail);
    }

    // Изменение статуса задач исполнителем
    public boolean changePerformerTaskStatus(String performerEmail, TaskStatus status, long taskId) {
        final String SQL_UPDATE_STATUS = "UPDATE tasks SET status_id=? WHERE performer_email=? AND id=?";
        int row = jdbcTemplate.update(SQL_UPDATE_STATUS, status.getId(), performerEmail, taskId);
        return row == 1;

    }

    // ------------------------------------------------- 6 пункт
    // Получение задач конкретного автора
    public List<Task> getTasksSpecificAuthor(String email, int page, int size) {
        final String SQL_GET_TASKS = "SELECT * FROM tasks WHERE author_email=? ORDER BY id ASC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(SQL_GET_TASKS, taskMapper, email, size, getOffset(page, size));
    }

    // Получение задач конкретного исполнителя
    public List<Task> getTasksSpecificPerformer(String email, int page, int size) {
        final String SQL_GET_TASK = "SELECT * " +
                " FROM tasks WHERE performer_email=? ORDER BY id ASC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(SQL_GET_TASK, taskMapper, email, size, getOffset(page, size));
    }

    // Поиск данных с фильтрацией
    public List<Task> searchTasks(Task task, int page, int size) {
        StringBuilder query = new StringBuilder();
        StringBuilder where = new StringBuilder();
        List<Object> values = new ArrayList<>();

        query.append("SELECT * FROM tasks");

        addField(where, "title", task.getTitle(), values, true);
        addField(where, "author_email", task.getAuthorEmail(), values, false);
        addField(where, "performer_email", task.getPerformerEmail(), values, false);

        if (task.getStatus() != null) {
            addField(where, "status_id", task.getStatus().getId(), values, false);
        }
        if (task.getPriority() != null) {
            addField(where, "priority_id", task.getPriority().getId(), values, false);
        }

        if (where.length() > 0) {
            query.append(" WHERE").append(where).append(" ORDER BY id").append(" ASC LIMIT ? OFFSET ?");
            values.add(size);
            values.add(getOffset(page, size));
        }
        return jdbcTemplate.query(query.toString(), taskMapper, values.toArray());
    }

    private void addField(StringBuilder where, String columnName, Object value,
                          List<Object> values, boolean text) {
        if (value != null) {
            if (where.length() > 0) {
                where.append(" AND");
            }
            if (text) {
                where.append(" ").append(columnName).append(" LIKE ?");
                values.add("%" + value + "%");
            } else {
                where.append(" ").append(columnName).append("=?");
                values.add(value);
            }
        }
    }

    private int getOffset(int page, int size) {
        return (page - 1) * size;
    }

    // TaskMapperClass
    private static class TaskMapper implements RowMapper<Task> {
        @Override
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            Task task = new Task();
            task.setId(rs.getLong("id"));
            task.setTitle(rs.getString("title"));
            task.setDescription(rs.getString("description"));
            task.setStatus(TaskStatus.getInstance(rs.getLong("status_id")));
            task.setPriority(TaskPriority.getInstance(rs.getLong("priority_id")));
            task.setAuthorEmail(rs.getString("author_email"));
            task.setPerformerEmail(rs.getString("performer_email"));
            return task;
        }
    }

}
