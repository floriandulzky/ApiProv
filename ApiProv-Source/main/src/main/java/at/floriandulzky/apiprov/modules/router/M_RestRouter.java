package at.floriandulzky.apiprov.modules.router;

import at.floriandulzky.apiprov.plugin.router.Router;
import at.floriandulzky.apiprov.plugin.router.exceptions.RouterException;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class M_RestRouter implements Router {

    @Override
    public Object handle(Map<String, List<String>> httpHeaders, Map<String, List<String>> queryParams,
                               String body, String method, Properties properties, String uri) throws RouterException {
        Client resteasyClient =  ResteasyClientBuilder.newBuilder().build();
        WebTarget target = resteasyClient.target(properties.getProperty("uri"));
        for(Map.Entry<String, List<String>> queryParam : queryParams.entrySet()){
            target = target.queryParam(queryParam.getKey(), queryParam.getValue());
        }
        Invocation.Builder request = target.request();
        for(Map.Entry<String, List<String>> httpHeader : httpHeaders.entrySet()){
            for(String value : httpHeader.getValue()){
                request.header(httpHeader.getKey(), value);
            }
        }
        if(method != "GET")
            return request.build(method, Entity.entity(body, MediaType.TEXT_PLAIN)).submit(String.class);
        else
            return request.buildGet().invoke(String.class);
    }

    @Override
    public String getName() {
        return "rest-router";
    }
}
