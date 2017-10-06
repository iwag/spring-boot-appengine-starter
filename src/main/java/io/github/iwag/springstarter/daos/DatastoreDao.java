package io.github.iwag.springstarter.daos;

import com.google.cloud.datastore.Cursor;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import io.github.iwag.springstarter.models.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatastoreDao implements TaskDao {

	@Autowired
    Datastore datastore;
    private KeyFactory keyFactory;


    @PostConstruct
    public void initializeKeyFactories() {
        keyFactory = datastore.newKeyFactory().setKind("User");
    }

    public DatastoreDao() {
//        datastore = DatastoreOptions.getDefaultInstance().getService(); // Authorized Datastore service
   //     keyFactory = datastore.newKeyFactory().setKind("Task2");      // Is used for creating keys later
    }

    public TaskEntity entityToTask(Entity entity) {
        return new TaskEntity.Builder()                                     // Convert to Task form
                .description(entity.getString(TaskEntity.DESCRIPTION))
                .id(entity.getKey().getId())
                .untilDate(entity.getString(TaskEntity.UNTIL_DATE))
                .build();
    }
    // [END entityToTask]
    // [START create]
    @Override
    public Long createTask(TaskEntity task) {
        IncompleteKey key = keyFactory.newKey();          // Key will be assigned once written
        FullEntity<IncompleteKey> incTaskEntity = Entity.newBuilder(key)  // Create the Entity
                .set(TaskEntity.DESCRIPTION, task.getDescription())
                .set(TaskEntity.UNTIL_DATE, task.getUntilDate())
                .set(TaskEntity.PRIORITY, task.getPriority())
                .build();
        Entity taskEntity = datastore.add(incTaskEntity); // Save the Entity
        return taskEntity.getKey().getId();                     // The ID of the Key
    }

    @Override
    public TaskEntity readTask(Long taskId) {
        Entity taskEntity = datastore.get(keyFactory.newKey(taskId)); // Load an Entity for Key(id)
        return entityToTask(taskEntity);
    }

    @Override
    public void updateTask(TaskEntity task) {
        Key key = keyFactory.newKey(task.getId());  // From a task, create a Key
        Entity entity = Entity.newBuilder(key)         // Convert Task to an Entity
                .set(TaskEntity.DESCRIPTION, task.getDescription())
                .set(TaskEntity.UNTIL_DATE, task.getUntilDate())
                .set(TaskEntity.PRIORITY, task.getPriority())
                .build();
        datastore.update(entity);                   // Update the Entity
    }

    @Override
    public void deleteTask(Long taskId) {
        Key key = keyFactory.newKey(taskId);        // Create the Key
        datastore.delete(key);                      // Delete the Entity
    }

    public List<TaskEntity> entitiesToTasks(QueryResults<Entity> resultList) {
        List<TaskEntity> resultTasks = new ArrayList<>();
        while (resultList.hasNext()) {  // We still have data
            resultTasks.add(entityToTask(resultList.next()));      // Add the Task to the List
        }
        return resultTasks;
    }

    @Override
    public List<TaskEntity> listTasks(String startCursorString) {
        Cursor startCursor = null;
        if (startCursorString != null && !startCursorString.equals("")) {
            startCursor = Cursor.fromUrlSafe(startCursorString);    // Where we left off
        }
        Query<Entity> query = Query.newEntityQueryBuilder()       // Build the Query
                .setKind("Task2")                                     // We only care about Tasks
                .setLimit(10)                                         // Only show 10 at a time
                .setStartCursor(startCursor)                          // Where we left off
                .setOrderBy(OrderBy.asc(TaskEntity.PRIORITY))                  // Use default Index "title"
                .build();
        QueryResults<Entity> resultList = datastore.run(query);   // Run the query
        List<TaskEntity> resultTasks = entitiesToTasks(resultList);     // Retrieve and convert Entities
        Cursor cursor = resultList.getCursorAfter();              // Where to start next time
        if (cursor != null && resultTasks.size() == 10) {         // Are we paging? Save Cursor
            String cursorString = cursor.toUrlSafe();               // Cursors are WebSafe
            return resultTasks;
        } else {
            return resultTasks;
        }
    }
}
