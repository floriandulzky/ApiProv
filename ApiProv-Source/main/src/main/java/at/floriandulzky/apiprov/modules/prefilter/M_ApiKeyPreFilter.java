package at.floriandulzky.apiprov.modules.prefilter;

import at.floriandulzky.apiprov.plugin.prefilter.PreFilter;
import at.floriandulzky.apiprov.plugin.prefilter.exceptions.PreFilter405Exception;
import at.floriandulzky.apiprov.plugin.prefilter.exceptions.PreFilter500Exception;
import at.floriandulzky.apiprov.plugin.prefilter.exceptions.PreFilterException;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class M_ApiKeyPreFilter implements PreFilter {

    @Override
    public String getName() {
        return "api-key";
    }

    @Override
    public void handle(Map<String, List<String>> httpHeaders, Map<String, List<String>> queryParams,
                       String body, String method, Properties properties, String uri) throws PreFilterException {
        try {
            String apikey = (String)properties.get("key");
            if(queryParams == null || !queryParams.containsKey("key") || queryParams.get("key") == null
            || queryParams.get("key").get(0) == null || !queryParams.get("key").get(0).equals(apikey)){
                    throw new PreFilter405Exception("Not allowed to access " + uri);
            }
        } catch(PreFilterException ex){
            throw ex;
        } catch (Exception ex){
            ex.printStackTrace();
            throw new PreFilter500Exception("Something went wrong at apikey prefilter");
        }
    }
}
