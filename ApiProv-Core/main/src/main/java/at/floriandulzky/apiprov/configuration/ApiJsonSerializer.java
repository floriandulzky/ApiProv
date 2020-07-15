package at.floriandulzky.apiprov.configuration;

import at.floriandulzky.apiprov.dto.Api;
import at.floriandulzky.apiprov.dto.ApiData;
import at.floriandulzky.apiprov.json.ApiDeserializer;
import at.floriandulzky.apiprov.json.ApiSerializer;
import io.quarkus.jsonb.JsonbConfigCustomizer;

import javax.inject.Singleton;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Properties;

@Singleton
public class ApiJsonSerializer implements JsonbConfigCustomizer {

    @Override
    public void customize(JsonbConfig jsonbConfig) {
        jsonbConfig.withSerializers(new ApiSerializer());
        jsonbConfig.withDeserializers(new ApiDeserializer());
    }

}
