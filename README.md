# Features
- CRUD RESTFul API
- Spring Boot
- Google cloud datastore

# how to get started

```
$ gcloud init 
# ...
GOOGLE_CLOUD_PROJECT=YOUR_PROJECT_ID mvn appengine:run
```

# First step of RestController

HelloController

```
@RestController
public class HelloController {
    @GetMapping(path = "/hello")
    public String hello() {
        return "Hello, World!";
    }
}
```

```
$ curl -i 'localhost:8080/hello'
HTTP/1.1 200 OK
Date: Thu, 31 Aug 2017 21:13:13 GMT
Content-Type: text/plain;charset=utf-8
Content-Length: 13
Server: Jetty(9.3.3.v20150827)

Hello, World!
```

# Handle a RestController

TaskController

```
@RestController
public class TaskController {

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

```

```
public class TaskEntity {
    private String id;
    private String description;
    private Integer priority;
    private String untilDate;

    public TaskEntity(String id, String description, Integer priority, String untilDate) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.untilDate = untilDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // define all getters and setters
}
```

```
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootApplication.class);
    }

}

```

```
@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }
}


```

If application can run in appengine, need appengine-web.xml in `src/main/webapp/WEB-INF/appengine-web.xml`

```
<?xml version="1.0" encoding="utf-8"?>
<appengine-web-app xmlns="http://appengine.google.com/ns/1.0">
  <threadsafe>true</threadsafe>
  <runtime>java8</runtime>
</appengine-web-app>
</runtime>
```

# Work with [Google cloud datastore](https://cloud.google.com/java/getting-started-appengine-standard/using-cloud-datastore)
