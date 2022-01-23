package ru.otus.spring.book.library.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.book.library.config.JobConfig;
import ru.otus.spring.book.library.config.MongoProperties;

@Service
@RequiredArgsConstructor
public class CleanUpServiceImpl implements CleanUpService {

    private final MongoProperties mongoProperties;


    @Override
    public void cleanUp() {
        try (MongoClient mongoClient = MongoClients.create("mongodb://" + mongoProperties.getHost() + ":" + mongoProperties.getPort())) {
            mongoClient.getDatabase(mongoProperties.getDatabase())
                    .getCollection(JobConfig.BOOK_WRITER_COLLECTION_NAME)
                    .drop();
        }
    }

}
