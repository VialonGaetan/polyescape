package fr.unice.polytech.pel.polyescape;

import fr.unice.polytech.pel.polyescape.Server.GameServerEndPoint;
import org.glassfish.tyrus.server.Server;

/**
 * @author Gaetan Vialon
 * Created the 11/01/2018.
 */
public class Main {

    public static void main(String[] args) {
        runServer();
    }

    public static void runServer() {
        Server server = new Server("localhost", 15555, "/websockets", GameServerEndPoint.class);
        boolean listening = true;
        try {
            server.start();
            System.out.print("Adresse du server ws://localhost:15555/websockets/gameserver");
        } catch (Exception e) {
            System.err.println("Could not listen on port: 15555.");
            System.exit(-1);
        }
        while (listening) ;
        server.stop();
    }
}
