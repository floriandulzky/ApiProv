package at.floriandulzky.apiprov.modules.router;

import at.floriandulzky.apiprov.plugin.router.Router;
import at.floriandulzky.apiprov.plugin.router.exceptions.RouterException;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class M_ShellRouter implements Router {

    @Override
    public Object handle(Map<String, List<String>> httpHeaders, Map<String, List<String>> queryParams, String body, String method, Properties properties, String uri) throws RouterException {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(
                    properties.getProperty("user"), properties.getProperty("host"),
                    Integer.parseInt(properties.getProperty("port"))
            );
            session.setConfig("StrictHostKeyChecking", "no");
            //Set password
            session.setPassword(properties.getProperty("password"));
            session.connect();
            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            // Set the command to execute on the channel and execute the command
            channelExec.setCommand((String) properties.get("script"));
            channelExec.connect();
            InputStream in = channelExec.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            // Command execution completed here.

            // Retrieve the exit status of the executed command
            int exitStatus = channelExec.getExitStatus();
            if (exitStatus > 0) {
                System.out.println("Remote script exec error! " + exitStatus);
            }
            //Disconnect the Session
            session.disconnect();
            return stringBuilder.toString();
        } catch (JSchException | IOException e) {
            throw new RouterException("could not exec shellscript - " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "shell-script";
    }
}
