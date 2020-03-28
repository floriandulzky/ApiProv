package at.floriandulzky.apiprov.modules.router;

import at.floriandulzky.apiprov.plugin.router.Router;
import at.floriandulzky.apiprov.plugin.router.exceptions.RouterException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class M_RestRouter implements Router {

    @Override
    public Object handle(Map<String, List<String>> httpHeaders, Map<String, List<String>> queryParams,
                               String body, String method, Properties properties, String uri) throws RouterException {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("test", "das ist ein Test");
        hashMap.put("test2", "Das ist Test 2");
        return hashMap;
    }

    @Override
    public String getName() {
        return "rest-router";
    }
}
