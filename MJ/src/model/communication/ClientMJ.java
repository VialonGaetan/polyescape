package model.communication;

import javax.websocket.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

@ClientEndpoint
public class MyClientEndPoint {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private final String message;

    public MyClientEndPoint(String message) {
        this.message = message;
    }

    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connected ... " + session.getId());
        try {
            session.getBasicRemote().sendText("start");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(Session session){
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            logger.info("Received ...." + message);
            String userInput = bufferRead.readLine();
            return userInput;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s close because of %s", session.getId(), closeReason));
    }

    /*public static void main(String[] args) {

        MyClientEndPoint myClientEndPoint = new MyClientEndPoint("lol");
        ClientManager client = ClientManager.createClient();
        try {
            client.connectToServer(myClientEndPoint, new URI("ws://localhost:15555/websockets/gameserver"));

        } catch (DeploymentException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }*/

}