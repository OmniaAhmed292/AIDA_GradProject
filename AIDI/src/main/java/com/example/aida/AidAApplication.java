package com.example.aida;


import com.example.aida.Entities.Product;
import com.example.aida.Repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Optional;


@SpringBootApplication
@EnableAsync
@EnableMongoAuditing

public class AidAApplication {

    public static void main(String[] args) {
        SpringApplication.run(AidAApplication.class, args);
    }
    @Bean

    CommandLineRunner runner(ProductRepository pRepository) {
        return args -> {

           Optional<Product> u= pRepository.findById("66398018452b382f9540087e");
            System.out.println(u.get().getCategoryName());
//            System.out.println(u.get().getLname());
//            System.out.println(u.get().getEmail());
//            System.out.println(u.get().getUserType());

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
//            MongoClient mongoClient = MongoClients.create();
//            MongoDatabase database = mongoClient.getDatabase("AIDA");
//            for (String name : database.listCollectionNames()) {
//                MongoCollection<Document> collection = database.getCollection(name);
//                collection.deleteMany(new Document());
//            }
            //------------------------------------------------------------------------
        };
    }
}
