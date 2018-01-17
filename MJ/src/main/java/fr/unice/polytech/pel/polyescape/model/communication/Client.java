package fr.unice.polytech.pel.polyescape.model.communication;

import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

@ClientEndpoint
public class Client {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private final String message;
    private String answer = "";

    public Client(String message) {
        this.message = message;
    }

    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connected ... " + session.getId());
        try {
            session.getBasicRemote().sendText(message);
            return;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @OnMessage
    public void onMessage(String message){
        this.answer = message;
        return;
    }

    public String getAnswer() {
        return answer;
    }

    public static class AnswerGetter extends Thread{

        private String answer;

        @Override
        public void run() {
            String request = "{\"request\":\"GET_ENIGME\"}";
            Client myClientEndPoint = new Client(request);
            ClientManager client = ClientManager.createClient();
            try {
                client.connectToServer(myClientEndPoint, new URI("ws://localhost:15555/websockets/gameserver"));
            } catch (DeploymentException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        public String getAnswer() {
            return answer;
        }
    }
}
