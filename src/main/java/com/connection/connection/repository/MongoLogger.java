package com.connection.connection.repository;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.time.LocalDateTime;

public class MongoLogger {

    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> collection;

    public MongoLogger() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("logdb");
        collection = database.getCollection("logs");
    }

    public void log(String operacao, String descricao) {
        Document doc = new Document()
                .append("operacao", operacao)
                .append("descricao", descricao)
                .append("timestamp", LocalDateTime.now().toString());
        collection.insertOne(doc);
    }

    public void close() {
        mongoClient.close();
    }
}