package at.floriandulzky.apiprov.dao;

import at.floriandulzky.apiprov.dto.Api;
import at.floriandulzky.apiprov.dto.ApiData;
import at.floriandulzky.apiprov.utils.ApiUtils;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.BsonArray;
import org.bson.BsonValue;
import org.bson.Document;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@RequestScoped
public class ConfigurationDAO {

    @Inject
    MongoClient mongoClient;

    public List<Api> getAllApis(){
        FindIterable<Document> findIterable = mongoClient.getDatabase("apiprov").getCollection("api").find();
        List<Api> apis = new ArrayList<>();
        for(Document document : findIterable){
            Api api = new Api();
            api.set_id(document.getString("_id"));
            Document apiDocument = ((Document)document.get(api.get_id()));
            ApiData apiData = new ApiData();
            apiData.setProperties(new ArrayList<>());
            for(Document propertiesDocument : (ArrayList<Document>) apiDocument.get("properties")){
                Properties properties = new Properties();
                for(Map.Entry<String, Object> entry : propertiesDocument.entrySet()){
                    properties.put(entry.getKey(), entry.getValue().toString());
                }
                apiData.getProperties().add(properties);
            }
            apiData.setPreFilter(apiDocument.getList("prefilters", String.class));
            apiData.setRouter(apiDocument.getString("router"));
            api.setApiData(apiData);
            apis.add(api);
        }
        return apis;
    }

    public void saveApi(Api api) {
        try {
            Document document = new Document();
            document.put("_id", api.get_id());
            Document apiData = new Document();
            apiData.put("properties", new BsonArray());
            for(Properties properties : api.getApiData().getProperties()){

            }
            apiData.put("prefilter", api.getApiData().getPreFilter());
            apiData.put("postfilter", api.getApiData().getPostFilter());
            apiData.put("router", api.getApiData().getRouter());
            document.put(api.get_id(), apiData);
            mongoClient.getDatabase("apiprov").getCollection("api").replaceOne(Filters.eq(api.get_id()), document);
        } catch(Exception ex){
            //TODO log
            ex.printStackTrace();
            throw ex;
        }
    }

}
