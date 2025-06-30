package com.banco.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexaoMongo {

    private static final String URI = "mongodb+srv://admin:adminadmin@cluster.uqqjxvv.mongodb.net/";
    private static final MongoClient client = MongoClients.create(URI); // só cria uma vez

    public static MongoDatabase getDatabase() {
        return client.getDatabase("logdb");
    }

    public static void close() {
        client.close();
    }
}