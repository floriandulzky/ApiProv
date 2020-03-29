package at.floriandulzky.apiprov.modules.router;

import at.floriandulzky.apiprov.plugin.router.Router;
import at.floriandulzky.apiprov.plugin.router.exceptions.RouterException;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class M_FixValueRouter implements Router {

    @Override
    public Object handle(Map<String, List<String>> httpHeaders, Map<String, List<String>> queryParams, String body,
                         String method, Properties properties, String uri) throws RouterException {
        return properties.get("data");
    }

    @Override
    public String getName() {
        return "fix value";
    }
}
