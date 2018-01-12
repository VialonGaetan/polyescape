package fr.unice.polytech.pel.polyescape.Server;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Gaetan Vialon
 * Created the 11/01/2018.
 */
@ServerEndpoint("/gameserver")
public class GameServerEndPoint {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connected ... " + session.getId());
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        System.out.println("Message Receive");
        System.out.println(message);
        return message + " sending by the server";
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }

}
