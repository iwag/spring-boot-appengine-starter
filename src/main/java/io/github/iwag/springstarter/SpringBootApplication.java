package io.github.iwag.springstarter;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import org.springframework.boot.SpringApplication;
import com.google.appengine.api.datastore.DatastoreService;
import org.springframework.context.annotation.Bean;

@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }

    @Bean
    public DatastoreService cloudDatastoreService() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        return datastore;
    }

}
