package io.github.iwag.springstarter.daos;



import io.github.iwag.springstarter.models.TaskEntity;

import java.sql.SQLException;
import java.util.List;

public interface TaskDao {
    Long createTask(TaskEntity task) throws SQLException;

    TaskEntity readTask(Long taskId) throws SQLException;

    void updateTask(TaskEntity task) throws SQLException;

    void deleteTask(Long taskId) throws SQLException;

    List<TaskEntity> listTasks(String startCursor) throws SQLException;
}