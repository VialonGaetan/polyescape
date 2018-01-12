package fr.unice.polytech.pel.polyescape;

import fr.unice.polytech.pel.polyescape.Server.GameServerEndPoint;
import org.glassfish.tyrus.server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Gaetan Vialon
 * Created the 11/01/2018.
 */
public class Main {

    public static void main(String[] args) {
        runServer();
    }

    public static void runServer() {
        Server server = new Server("localhost", 8025, "/websockets", GameServerEndPoint.class);
        boolean listening = true;

        try {
            server.start();
            System.out.print("Adresse du server ws://localhost:8025/websockets/gameserver");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        while (listening);
        server.stop();
    }
}
