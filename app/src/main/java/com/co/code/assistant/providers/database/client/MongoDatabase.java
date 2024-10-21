package com.co.code.assistant.providers.database.client;

import com.co.code.assistant.core.repositories.database.ISuggestionDatabaseRepository;
import com.co.code.assistant.providers.items.dto.ISuggestionDto;
import com.co.code.assistant.providers.items.dto.SuggestionDto;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import io.reactivex.rxjava3.core.Observable;
import org.bson.Document;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Singleton
public class MongoDatabase implements ISuggestionDatabaseRepository<Observable<List<ISuggestionDto>>, Map<String, String>> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private com.mongodb.client.MongoDatabase mongoDb ;
    private final String connectionString = "mongodb://appUser:appPassword123@localhost:27017";

    @Inject
    public MongoDatabase() {



    }


    @Override
    public Observable<List<ISuggestionDto>> getInformation(Map<String, String> info) {
        return Observable.fromCallable(() -> {
            List<ISuggestionDto> logs = new ArrayList<>();
            try (MongoClient mongoClient = MongoClients.create(new ConnectionString(connectionString))) {
                mongoDb = mongoClient.getDatabase("testdb");
                MongoCollection<Document> collection = mongoDb.getCollection("logs");


                for (Document doc : collection.find()) {
                    String id = doc.getString("info");
                    String name = doc.getString("content");
                    ISuggestionDto dto = SuggestionDto.builder().id(id).content(name).build();

                    logs.add(dto);
                }
            }


            return logs;

        });
    }

    @Override
    public void setInformation(Map<String, String> info) {
        try (MongoClient mongoClient = MongoClients.create(new ConnectionString(connectionString))) {
            mongoDb = mongoClient.getDatabase("testdb");
            String dateId = LocalDateTime.now().format(formatter);
            MongoCollection<Document> collection = mongoDb.getCollection("logs");
            info
                    .entrySet()
                    .stream()
                    .forEach(keyset -> {
                        Document doc = new Document("info", dateId)
                                .append("content", keyset.getValue());
                        collection.insertOne(doc);
                    });
        }

    }
}
