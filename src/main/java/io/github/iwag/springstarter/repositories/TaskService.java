package io.github.iwag.springstarter.repositories;



import io.github.iwag.springstarter.models.TaskEntity;

import java.sql.SQLException;
import java.util.List;

public interface TaskService {
    Long createTask(TaskEntity task);

    TaskEntity readTask(Long taskId);

    void updateTask(TaskEntity task);

    void deleteTask(Long taskId);

    List<TaskEntity> listTasks(String startCursor);
}
