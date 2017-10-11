package io.github.iwag.springstarter.controllers;

import io.github.iwag.springstarter.daos.TaskService;
import io.github.iwag.springstarter.models.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class TaskController extends BaseController {

    @Autowired
    TaskService datastoreService;

    private final Random rand  = new Random(); // to generate id

    @CrossOrigin
    @RequestMapping(path = "/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskEntity> gets() {
        return datastoreService.listTasks(null);
    }

	@CrossOrigin
    @RequestMapping(path = "/task", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody TaskEntity task) {
        logger.info("TaskEntity: " + task);

        Long l = rand.nextLong();
        task.setId(l.toString());

        datastoreService.createTask(task);

        return;
    }
}
