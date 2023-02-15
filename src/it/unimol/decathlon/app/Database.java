package it.unimol.decathlon.app;

import com.mongodb.*;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.File;
import java.nio.file.Files;
import java.util.*;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;

import static com.mongodb.client.model.Filters.*;

public class Database {

    private ConnectionString connectionString;
    private MongoClientSettings settings;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> savings;
    private static Database instance;

    private Database() {
        ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("org.mongodb.driver").setLevel(Level.ERROR);
        this.connectionString = new ConnectionString("mongodb://root:automated@database:27017/");
        this.settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        this.mongoClient = MongoClients.create(settings);
        this.database = mongoClient.getDatabase("decathlon");
        this.savings = database.getCollection("savings");
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public List<Document> getSavings(){
        List<Document> list = this.savings.find()
                .into(new ArrayList<>());
        return list;

    }

    public void addSavings(File file) {
        try {
            byte[] data = Files.readAllBytes(file.toPath());
            Document doc = new Document("_id", new ObjectId());
            doc.append("name", Calendar.getInstance().getTime())
                    .append("file", data);
            this.savings.insertOne(doc);
        } catch (Exception e) {}
    }

    public File getFile(String name){
        Document doc = this.savings.find(eq("name", name))
                .first();
        return (File) doc.get("file");
    }

}