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
    public TaskEntity get(@PathVariable("id") String id) {
        return datastoreService.readTask(Long.valueOf(id));
    }

    @CrossOrigin
    @RequestMapping(path = "/task", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody TaskEntity task) {
        logger.info("TaskEntity: " + task);

        datastoreService.createTask(task);

        return;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.DELETE, path = "/task/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable("id") String id) {
        datastoreService.deleteTask(Long.valueOf(id));
    }

    @CrossOrigin
    @RequestMapping(path = "/task", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody TaskEntity task) {
        logger.info("TaskEntity: " + task);

        datastoreService.updateTask(task);

        return;
    }

}
