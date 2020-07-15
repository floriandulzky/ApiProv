package at.floriandulzky.apiprov.utils;

import org.bson.Document;

import java.util.Properties;

public class ApiUtils {

    public static Properties createProperties(Iterable<Document> documents){
        Properties properties = new Properties();
        if(documents != null)
            for(Document doc : documents){
                for(String key : doc.keySet()){
                    if(doc.get(key) instanceof Iterable){
                        properties.putAll(ApiUtils.createProperties((Iterable<Document>) doc.get(key)));
                    } else {
                        properties.put(key, doc.get(key));
                    }
                }
            }
        return properties;
    }

}
