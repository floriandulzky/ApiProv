package at.floriandulzky.apiprov.json;

import at.floriandulzky.apiprov.dto.Api;

import javax.json.JsonValue;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;

public class ApiSerializer implements JsonbSerializer<Api> {

    @Override
    public void serialize(Api api, JsonGenerator jsonGenerator, SerializationContext serializationContext) {
        jsonGenerator.writeStartObject();
        jsonGenerator.write("_id", api.get_id());
        Jsonb jsonb = JsonbBuilder.create(new JsonbConfig());
        jsonGenerator.write(api.get_id(), jsonb.fromJson(jsonb.toJson(api.getApiData()), JsonValue.class));
        jsonGenerator.writeEnd();
    }

}
