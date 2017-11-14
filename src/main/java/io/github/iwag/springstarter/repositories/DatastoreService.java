package io.github.iwag.springstarter.repositories;

import com.google.appengine.api.datastore.*;
import io.github.iwag.springstarter.models.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class DatastoreService implements TaskService {

    private final static String KIND = "Task2";

    @Autowired
    com.google.appengine.api.datastore.DatastoreService datastore;

    public TaskEntity entityToTask(Entity entity) {
        return new TaskEntity.Builder()
                .description((String) entity.getProperty(TaskEntity.DESCRIPTION))
                .id(entity.getKey().getId())
                .untilDate((String) entity.getProperty(TaskEntity.UNTIL_DATE))
                .build();
    }

    @Override
    public Long createTask(TaskEntity task) {
        Entity incTaskEntity = new Entity(KIND);
        incTaskEntity.setProperty(TaskEntity.DESCRIPTION, task.getDescription());
        incTaskEntity.setProperty(TaskEntity.UNTIL_DATE, task.getUntilDate());
        incTaskEntity.setProperty(TaskEntity.PRIORITY, task.getPriority());
        Key k = datastore.put(incTaskEntity);
        return k.getId();
    }

    @Override
    public TaskEntity readTask(Long taskId) {
        Entity taskEntity = null;
        try {
            taskEntity = datastore.get(KeyFactory.createKey(KIND, taskId));
        } catch (EntityNotFoundException e) {
            return null;
        }
        return entityToTask(taskEntity);
    }

    @Override
    public void updateTask(TaskEntity task) {
        Key key = KeyFactory.createKey(KIND, task.getId());
        Entity entity = new Entity(key);
        entity.setProperty(TaskEntity.DESCRIPTION, task.getDescription());
        entity.setProperty(TaskEntity.UNTIL_DATE, task.getUntilDate());
        entity.setProperty(TaskEntity.PRIORITY, task.getPriority());
        datastore.put(entity);
    }

    @Override
    public void deleteTask(Long taskId) {
        Key key = KeyFactory.createKey(KIND, taskId);
        datastore.delete(key);
    }

    public List<TaskEntity> entitiesToTasks(Iterator<Entity> resultList) {
        List<TaskEntity> resultTasks = new ArrayList<>();
        while (resultList.hasNext()) {
            resultTasks.add(entityToTask(resultList.next()));
        }
        return resultTasks;
    }

    @Override
    public List<TaskEntity> listTasks(String startCursorString) {
        FetchOptions fetchOptions = FetchOptions.Builder.withLimit(10);
        if (startCursorString != null && !startCursorString.equals("")) {
            fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString));
        }
        Query query = new Query(KIND)
                .addSort(TaskEntity.DESCRIPTION, Query.SortDirection.ASCENDING);
        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

        List<TaskEntity> resultBooks = entitiesToTasks(results);
        Cursor cursor = results.getCursor();
        if (cursor != null && resultBooks.size() == 10) {
            String cursorString = cursor.toWebSafeString();
            return resultBooks;
        } else {
            return resultBooks;
        }
    }
}
