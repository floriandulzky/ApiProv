package at.floriandulzky.apiprov.plugin.prefilter;

import at.floriandulzky.apiprov.plugin.Plugin;
import at.floriandulzky.apiprov.plugin.prefilter.exceptions.PreFilterException;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public interface PreFilter extends Plugin {

    void handle(Map<String, List<String>> httpHeaders, Map<String, List<String>> queryParams, String body, String method,
                Properties properties, String uri) throws PreFilterException;

}
