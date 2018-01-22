package fr.unice.polytech.pel.polyescape.Server;

import fr.unice.polytech.pel.polyescape.Data.Joueur;
import fr.unice.polytech.pel.polyescape.Data.Partie;
import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import fr.unice.polytech.pel.polyescape.Transmission.TypeRequest;
import fr.unice.polytech.pel.polyescape.Transmission.requests.HelpRequest;
import fr.unice.polytech.pel.polyescape.Transmission.requests.Request;
import fr.unice.polytech.pel.polyescape.Transmission.requests.RequestFactory;
import org.json.JSONObject;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
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
    public String onMessage(String message, Session session) throws IOException {
        System.out.println(message);
        logger.info("Message Receive : " + message);
        Request request = new RequestFactory().createTypeRequest(message, session);
        logger.info("Message send : " + request.getAnswer());
        return request.getAnswer();
    }


    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }

}
