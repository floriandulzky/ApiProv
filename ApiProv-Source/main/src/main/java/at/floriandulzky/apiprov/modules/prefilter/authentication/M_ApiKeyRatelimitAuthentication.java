package at.floriandulzky.apiprov.modules.prefilter.authentication;

import at.floriandulzky.apiprov.plugin.prefilter.exceptions.PreFilter405Exception;
import at.floriandulzky.apiprov.plugin.prefilter.exceptions.PreFilter500Exception;
import at.floriandulzky.apiprov.plugin.prefilter.exceptions.PreFilterException;
import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClientFactory;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoClientImpl;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class M_ApiKeyRatelimitAuthentication extends M_ApiKeyAuthentication {

    @Override
    public void handle(Map<String, List<String>> httpHeaders, Map<String, List<String>> queryParams,
                       String body, String method, Properties properties, String uri) throws PreFilterException {
        super.handle(httpHeaders, queryParams, body, method, properties, uri);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://localhost:27017"))
                .build();
        try (MongoClient mongoClient = new MongoClientImpl( mongoClientSettings , null )){
            MongoDatabase mongoDatabase = mongoClient.getDatabase("apiprov");
            Document document = mongoDatabase.getCollection("moduledata").find(Filters.eq(properties.getProperty("key") + "-" + uri)).first();
            int apicounter = 1;
            if(document != null){
                Document count = (Document) document.get("count");
                if(count != null){
                    apicounter = count.getInteger("counter");
                    count.put("counter", ++apicounter);
                    long lastApiCall = count.getLong("lastApiCall");
                    count.put("lastApiCall", System.currentTimeMillis());
                    if(apicounter > Integer.parseInt(properties.getProperty("callsPerXSecond"))){
                        if((System.currentTimeMillis() - lastApiCall) <= (Long.parseLong(properties.getProperty("seconds")) * 1000)){
                            throw new PreFilter405Exception("Too much requests in  the last " + properties.getProperty("seconds") + " seconds");
                        } else {
                            count.put("counter", 1);
                        }
                    }
                }
                mongoDatabase.getCollection("moduledata").replaceOne(Filters.eq(properties.getProperty("key") + "-" + uri), document);
            } else {
                document = new Document();
                document.put("_id", properties.getProperty("key") + "-" + uri);
                Document count = new Document();
                count.put("counter", 1);
                count.put("lastApiCall", System.currentTimeMillis());
                document.put("count", count);
                mongoDatabase.getCollection("moduledata").insertOne(document);
            }

        } catch (MongoException ex) {
            throw new PreFilter500Exception("Something went wrong");
        }
    }

}
