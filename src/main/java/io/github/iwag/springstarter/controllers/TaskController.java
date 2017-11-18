package io.github.iwag.springstarter.controllers;

import io.github.iwag.springstarter.repositories.TaskService;
import io.github.iwag.springstarter.models.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class TaskController extends BaseController {

    @Autowired
    TaskService datastoreService;

    @CrossOrigin
    @RequestMapping(path = "/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskEntity> gets() {
        return datastoreService.listTasks(null);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/task/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskEntity get(@PathVariable(name = "id", required = true) String id) {
        return datastoreService.readTask(Long.valueOf(id));
    }

    @CrossOrigin
    @RequestMapping(path = "/task", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TaskEntity create(@RequestBody(required = true) TaskEntity task) {
        logger.info("TaskEntity: " + task);

        Optional<Long> id = datastoreService.createTask(task);
        id.ifPresent(i -> task.setId(i.toString()));

        return task;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.DELETE, path = "/task/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable(name = "id", required = true) String id) {
        datastoreService.deleteTask(Long.valueOf(id));
    }

    @CrossOrigin
    @RequestMapping(path = "/task", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody(required = true) TaskEntity task) {
        logger.info("TaskEntity: " + task);

        datastoreService.updateTask(task);

        return;
    }

}
