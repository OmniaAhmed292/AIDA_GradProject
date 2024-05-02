package com.example.aida;


import com.example.aida.Repositories.VendorRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
@EnableMongoAuditing
public class AidAApplication {

    public static void main(String[] args) {
        SpringApplication.run(AidAApplication.class, args);
    }
    @Bean
    CommandLineRunner runner(VendorRepository userRepository) {
        return args -> {
//            User user = new User();
//            user.setFname("John");
//            user.setLname("Doe");
//            user.setEmail("johndoe@gmail.com");
//            user.setHashedPassword("password");
//            user.setUserType("vendor");
//            userRepository.insert(user);

            //------------------------------------------------------------------------
            //Database Clean initialiser
            // TODO: Delete before production
            MongoClient mongoClient = MongoClients.create();
            MongoDatabase database = mongoClient.getDatabase("AIDA");
            for (String name : database.listCollectionNames()) {
                MongoCollection<Document> collection = database.getCollection(name);
                collection.deleteMany(new Document());
            }
            //------------------------------------------------------------------------
        };
    }
}
