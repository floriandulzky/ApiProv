package at.floriandulzky.apiprov.dto;

import java.util.List;
import java.util.Properties;

public class ApiData {

    private List<String> preFilter;
    private List<String> postFilter;
    private String router;
    private List<Properties> properties;

    public List<String> getPreFilter() {
        return preFilter;
    }

    public void setPreFilter(List<String> preFilter) {
        this.preFilter = preFilter;
    }

    public List<String> getPostFilter() {
        return postFilter;
    }

    public void setPostFilter(List<String> postFilter) {
        this.postFilter = postFilter;
    }

    public String getRouter() {
        return router;
    }

    public void setRouter(String router) {
        this.router = router;
    }

    public List<Properties> getProperties() {
        return properties;
    }

    public void setProperties(List<Properties> properties) {
        this.properties = properties;
    }
}
