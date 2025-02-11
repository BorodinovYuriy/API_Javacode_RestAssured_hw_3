package org.ex.helpers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ex.config.PropertiesLoader;

public class MongoDB {
    private static MongoClient mongoClient;
    private final MongoDatabase database;

    public MongoDB() {
        if (mongoClient == null) {
            synchronized (MongoDB.class) {
                if (mongoClient == null) {
                    mongoClient = MongoClients.create(
                            PropertiesLoader.getMongoUri()
                    );
                }
            }
        }
        this.database = mongoClient.getDatabase(PropertiesLoader.getMongoDbName());
    }
    public MongoCollection<Document> getCollection(String collectionName) {
        return database.getCollection(collectionName);
    }
    public Document getDocQueryInMongo(String collectionName, int RequiredId, String fieldIdInMongo){
        MongoCollection<Document> userCollection = getCollection(collectionName);
        Document query = new Document(fieldIdInMongo, RequiredId);
        return userCollection.find(query).first();
    }
    public void deleteDocumentById(String collectionName, int id) {
        MongoCollection<org.bson.Document> collection = database.getCollection(collectionName);
        Bson query = Filters.eq("_id", id);
        collection.deleteOne(query);
    }

    public static void closeMongoClient() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }
}
