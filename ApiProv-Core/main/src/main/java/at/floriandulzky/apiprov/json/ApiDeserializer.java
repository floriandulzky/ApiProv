package at.floriandulzky.apiprov.json;

import at.floriandulzky.apiprov.dto.Api;
import at.floriandulzky.apiprov.dto.ApiData;

import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonParser;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class ApiDeserializer implements JsonbDeserializer<Api> {

    @Override
    public Api deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Type type) {
        Api api = new Api();
        JsonObject jsonObject = jsonParser.getObject();
        api.set_id(jsonObject.getString("_id"));
        ApiData apiData = new ApiData();
        if(jsonObject.containsKey(api.get_id())){
            JsonObject innerJsonObj = jsonObject.getJsonObject(api.get_id());
            if(!innerJsonObj.containsKey("router"))
                throw new IllegalStateException("router ust be set");
            apiData.setRouter(innerJsonObj.getString("router"));
            apiData.setPreFilter(new ArrayList<String>());
            if(innerJsonObj.containsKey("prefilter"))
                for(Object preFilter : innerJsonObj.getJsonArray("prefilter").toArray()){
                    apiData.getPreFilter().add((String)preFilter);
                }
            apiData.setPostFilter(new ArrayList<String>());
            if(innerJsonObj.containsKey("postfilter"))
                for(Object preFilter : innerJsonObj.getJsonArray("postfilter").toArray()){
                    apiData.getPreFilter().add((String)preFilter);
                }
            Properties properties = new Properties();
            if(innerJsonObj.containsKey("properties") && !innerJsonObj.getJsonArray("properties").isEmpty()){
                for(JsonValue propertyValue : innerJsonObj.getJsonArray("properties")){
                    for(Map.Entry<String, JsonValue> preFilter : propertyValue.asJsonObject().entrySet()){
                        properties.put(preFilter.getKey(), ((JsonString)preFilter.getValue()).getString());
                    }
                }
                apiData.setProperties(Arrays.asList(properties));
            }
            api.setApiData(apiData);
            return api;
        }
        throw new IllegalStateException("Wrong json format");
    }

}
