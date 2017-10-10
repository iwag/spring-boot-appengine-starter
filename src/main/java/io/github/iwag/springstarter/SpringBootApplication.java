package io.github.iwag.springstarter;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.cloud.NoCredentials;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import org.springframework.boot.SpringApplication;
import com.google.appengine.api.datastore.DatastoreService;
import org.springframework.context.annotation.Bean;
		  import com.google.cloud.ServiceOptions;

@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }

    @Bean
    public DatastoreService cloudDatastoreService() {
	String projectId = ServiceOptions.getDefaultProjectId();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//               DatastoreOptions.getDefaultInstance().getService();

        return datastore;
    }

}

