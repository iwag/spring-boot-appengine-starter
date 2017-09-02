package io.github.iwag.springstarter.controllers;

import io.github.iwag.springstarter.models.TaskEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController extends BaseController  {

    @RequestMapping(path = "/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskEntity gets() {
        return new TaskEntity("0", "TEST", 0, "2017/08/31");
    }

    @RequestMapping(path = "/task", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody TaskEntity task) {
        logger.info("TaskEntity: "+task);
        return;
    }
}
