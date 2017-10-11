# Overview
![](https://i.gyazo.com/9822069d8fe48e84ee3c8c11d57d6e68.png)

- CRUD RESTFul API
- Spring Boot
- Google cloud datastore
- Google appengine Standard Env.

# how to get started

```bash
$ gcloud init 
# ...
$ mvn appengine:devserver # run locally
$ mvn appengine:deploy # deploy on GCP

```

# First step, Simple RestController

Firstly, create HelloController which returns only `Hello, World!` text by GET Request.
This is same as [spring boot's guide](https://spring.io/guides/gs/spring-boot/#_create_a_simple_web_application)

```java
@RestController
public class HelloController {
    @GetMapping(path = "/hello")
    public String hello() {
        return "Hello, World!";
    }
}
```

In order to run as spring-boot, ServletInitializer and SpringApplication requires.

```java
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootApplication.class);
    }

}

```

```java
@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }
}

```

To allow the application run in appengine, we need appengine-web.xml in `src/main/webapp/WEB-INF/appengine-web.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<appengine-web-app xmlns="http://appengine.google.com/ns/1.0">
  <threadsafe>true</threadsafe>
  <runtime>java8</runtime>
</appengine-web-app>
```

Run this in appengine. You can see something like below.

```bash
$ mvn appengine:run
$ curl -i 'localhost:8080/hello'
HTTP/1.1 200 OK
Date: Thu, 31 Aug 2017 21:13:13 GMT
Content-Type: text/plain;charset=utf-8
Content-Length: 13
Server: Jetty(9.3.3.v20150827)

Hello, World!
```

# Handle CRUD by a RestController

For example, we make a API to manage several tasks. 
Now, TaskController(which is kind of RestController) handles GET/POST/DELETE/PUT HTTP Request where datastore (or persistansis) is not assumed to simplify.

```java
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

Here's an entity class.

```java
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

### Work with [Google cloud datastore](https://cloud.google.com/java/getting-started-appengine-standard/using-cloud-datastore)

Append the following dependency into `dependency` part in pom.xml.

```xml
<dependency>
   <groupId>com.google.appengine</groupId>
   <artifactId>appengine-api-1.0-sdk</artifactId>
   <version>1.9.50</version>
</dependency>
```

We use datastore through appengine-api library instead of following cloud library 
because cloud library can't be worked with local devserver's datastore.

```xml
<dependency>   <!-- Google Cloud Client Library for Java -->
   <groupId>com.google.cloud</groupId>
   <artifactId>google-cloud</artifactId>
   <version>0.25.0-alpha</version>
</dependency>
```
