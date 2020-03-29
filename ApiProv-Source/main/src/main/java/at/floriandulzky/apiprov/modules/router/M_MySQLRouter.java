package at.floriandulzky.apiprov.modules.router;

import at.floriandulzky.apiprov.plugin.router.Router;
import at.floriandulzky.apiprov.plugin.router.exceptions.RouterException;

import java.net.URLDecoder;
import java.sql.*;
import java.util.*;

public class M_MySQLRouter implements Router {

    @Override
    public Object handle(Map<String, List<String>> httpHeaders, Map<String, List<String>> queryParams, String body, String method, Properties properties, String uri) throws RouterException {
        try{
            String connectionCommand =
                    "jdbc:mysql://" + properties.getProperty("host") + ":" +
                            properties.getProperty("port") +
                            "/" + properties.getProperty("database") +
                            "?user=" + properties.getProperty("user") +
                            "&password=" + properties.getProperty("password");
            Connection connection = DriverManager.getConnection(connectionCommand);
            PreparedStatement statement = connection.prepareStatement(properties.getProperty("query"));
            if(body != null) {
                body = URLDecoder.decode(body, "UTF-8");
                for(String string : body.split(";")){
                    String[] param = string.split("=");
                    if(param != null && param[1].matches("[0-9]*")){
                        statement.setLong(Integer.parseInt(param[0]), Long.parseLong(param[1]));
                    } else {
                        statement.setString(Integer.parseInt(param[0]), param[1]);
                    }
                }
            }
            ResultSet rs = statement.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
            while (rs.next()){
                Map<String, Object> row = new HashMap<String, Object>(columns);
                for(int i = 1; i <= columns; ++i){
                    row.put(md.getColumnName(i), rs.getObject(i));
                }
                rows.add(row);
            }
            return rows;
        }catch(Exception ex){
            throw new RouterException("could not execute query - " + ex.getMessage());
        }
    }

    @Override
    public String getName() {
        return null;
    }
}
