package fr.unice.polytech.pel.polyescape;

import fr.unice.polytech.pel.polyescape.server.GameServerEndPoint;
import org.glassfish.tyrus.server.Server;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gaetan Vialon
 * Created the 11/01/2018.
 */
public class Main {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public static void main(String[] args) {
        runServer();
    }

    public static void runServer() {
        Server server = new Server("localhost", 15555, "/websockets", GameServerEndPoint.class);
        boolean listening = true;
        try {
            server.start();
            Logger.getGlobal().info("Adresse du server ws://localhost:15555/websockets/gameserver");
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING,"Could not listen on port: 15555.");
            System.exit(-1);
        }
        while (listening);
        server.stop();
    }
}
