package at.floriandulzky.apiprov.service;

import at.floriandulzky.apiprov.plugin.postfilter.PostFilter;
import at.floriandulzky.apiprov.plugin.prefilter.PreFilter;
import at.floriandulzky.apiprov.plugin.prefilter.exceptions.PreFilterException;
import at.floriandulzky.apiprov.plugin.router.Router;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.*;
import java.util.*;

@RequestScoped
public class AppControllerService {

    @Inject
    MongoClient mongoClient;

    public Response handleRequest(String path, UriInfo uriInfo, HttpHeaders httpHeaders, String method, String body) {
        Document document = mongoClient.getDatabase("apiprov").getCollection("api").find(Filters.eq(path)).first();
        if(document != null) {
            Document api = ((Document) document.get(path));
            try {
                this.handlePreFilters(api, httpHeaders, uriInfo, body, method);
                Object routerResult = this.handleRouter(api, httpHeaders, uriInfo, body, method);
                return Response.ok(this.handlePostFilter(api, routerResult)).build();
            } catch(PreFilterException ex) {
                return Response.status(ex.getStatus(), ex.getMessage()).build();
            } catch(Exception ex){
                ex.printStackTrace();
                return Response.status(500, ex.getMessage()).build();
            }
        } else {
            return Response.status(404, "API not found").build();
        }
    }

    private void handlePreFilters(Document api, HttpHeaders httpHeaders, UriInfo uriInfo, String body, String method)
            throws Exception {
        List<String> prefilters = (ArrayList<String>) api.get("prefilter");
        Properties properties = this.createProperties((ArrayList<Document>) api.get("properties"));
        Map<String, List<String>> httpHeaderMap = this.createHttpHeaders(httpHeaders);
        Map<String, List<String>> queryParams = this.createQueryParams(uriInfo);
        for(String prefilter : prefilters){
            Class clazz = Class.forName(prefilter);
            ((PreFilter)clazz.getDeclaredConstructor().newInstance()).handle(
                    httpHeaderMap, queryParams, body, method, properties, uriInfo.getRequestUri().toASCIIString()
            );
        }
    }

    private Object handleRouter(Document api, HttpHeaders httpHeaders, UriInfo uriInfo, String body, String method)
            throws Exception{
        String router = (String) api.get("router");
        Class clazz = Class.forName(router);
        return ((Router)clazz.getDeclaredConstructor().newInstance()).handle(
                this.createHttpHeaders(httpHeaders), this.createQueryParams(uriInfo), body, method,
                this.createProperties((ArrayList<Document>) api.get("properties")), uriInfo.getRequestUri().toASCIIString()
        );
    }

    private Object handlePostFilter(Document api, Object routerResult) throws Exception{
        String postfilter = (String) api.get("postfilter");
        try{
            if(postfilter == null)
                return routerResult;
            Class clazz = Class.forName(postfilter);
            return ((PostFilter)clazz.getDeclaredConstructor().newInstance()).handle(
                    routerResult
            );
        }catch (ClassNotFoundException ex){
            return routerResult;
        }
    }

    private Map<String, List<String>> createQueryParams(UriInfo uriInfo) {
        if(uriInfo != null && uriInfo.getQueryParameters() != null){
            Map<String, List<String>> result = new HashMap<>();
            for(Map.Entry<String, List<String>> entry : uriInfo.getQueryParameters().entrySet()){
                result.put(entry.getKey(), entry.getValue());
            }
            return result;
        }
        return null;
    }

    private Map<String, List<String>> createHttpHeaders(HttpHeaders httpHeaders) {
        if(httpHeaders != null && httpHeaders.getRequestHeaders() != null){
            Map<String, List<String>> result = new HashMap<>();
            for(Map.Entry<String, List<String>> entry : httpHeaders.getRequestHeaders().entrySet()){
                result.put(entry.getKey(), entry.getValue());
            }
            return result;
        }
        return null;
    }

    private Properties createProperties(Iterable<Document> documents){
        Properties properties = new Properties();
        for(Document doc : documents){
            for(String key : doc.keySet()){
                if(doc.get(key) instanceof Iterable){
                    properties.putAll(this.createProperties((Iterable<Document>) doc.get(key)));
                } else {
                    properties.put(key, doc.get(key));
                }
            }
        }
        return properties;
    }

}
