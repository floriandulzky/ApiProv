package at.floriandulzky.apiprov.plugin.router;

import at.floriandulzky.apiprov.plugin.Plugin;
import at.floriandulzky.apiprov.plugin.router.exceptions.RouterException;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public interface Router extends Plugin {

    Object handle(Map<String, List<String>> httpHeaders, Map<String, List<String>> queryParams, String body, String method,
                Properties properties, String uri) throws RouterException;

}
