package io.github.iwag.springstarter;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
		  import com.google.cloud.ServiceOptions;

@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }

    @Bean
    public Datastore cloudDatastoreService() {
	String projectId = ServiceOptions.getDefaultProjectId();
        Datastore datastore =
               DatastoreOptions.getDefaultInstance().getService();
    //    Datastore datastore = DatastoreOptions.newBuilder().setProjectId(projectId).build().getService();

        return datastore;
    }

}

