package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.Arrays;

public class Main {
    MongoCollection<Document> collection;
    public String dbConnection(){
        String connectionString = "mongodb+srv://ody:Ody12345@cluster0.guk08bz.mongodb.net/?retryWrites=true&w=majority";
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase database = mongoClient.getDatabase("blog");
            collection = database.getCollection("posts");
            return "connected";
        }
    }
    private void dbInsert(){
        Document document = new Document("name", "soduari")
            .append("age", 25)
            .append("city", "New York");
        collection.insertOne(document);
        System.out.println("Document inserted successfully.");
    }
    private void createIndexes(){
        collection.createIndex(new Document("name", 1));
        System.out.println("Index created successfully.");
    }
    private void dbFind() {
        Document foundDocument = collection.find(Filters.eq("name", "soduari")).first();
        System.out.println("Found document: " + foundDocument.toJson());
    }
    private void dbUpdate() {
        collection.updateOne(Filters.eq("name", "soduari"), new Document("$set", new Document("city", "Los Angeles")));
        System.out.println("Document updated successfully.");
    }
    private void dbDelete() {
        collection.deleteOne(Filters.eq("name", "soduari"));
        System.out.println("Document deleted successfully.");
    }
    private void dbAggregate() {
        Bson matchStage = Aggregates.match(Filters.eq("name:","soduari"));
        collection.aggregate(Arrays.asList(matchStage)).forEach(document -> System.out.println(document.toJson()));
        System.out.println("Aggregation result:");
    }
    public static void main(String[] args) {
        Main main = new Main();
        main.dbConnection();
    }
}

