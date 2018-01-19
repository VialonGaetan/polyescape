package fr.unice.polytech.pel.polyescape.Server;

import fr.unice.polytech.pel.polyescape.Data.GameMaster;
import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import fr.unice.polytech.pel.polyescape.Transmission.TypeRequest;
import fr.unice.polytech.pel.polyescape.Transmission.requests.HelpRequest;
import fr.unice.polytech.pel.polyescape.Transmission.requests.HelpResponseRequest;
import fr.unice.polytech.pel.polyescape.Transmission.requests.Request;
import fr.unice.polytech.pel.polyescape.Transmission.requests.RequestFactory;
import org.json.JSONObject;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.lang.reflect.Type;
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
        if (new JSONObject(message).getString(JsonArguments.REQUEST.toString()).equals(TypeRequest.GET_PARTIES.toString())){
            Gestionnaire.getInstance().setSessionMG(session);
        }
        if (new JSONObject(message).getString(JsonArguments.REQUEST.toString()).equals(TypeRequest.HELP.toString())){
            HelpRequest helpRequest = new HelpRequest(message, Gestionnaire.getInstance().getSessionMG());
            System.out.println(Gestionnaire.getInstance().getSessionMG() == null);
            Gestionnaire.getInstance().getSessionMG().getBasicRemote().sendText(helpRequest.getAnswer());
        }
        if (new JSONObject(message).getString(JsonArguments.REQUEST.toString()).equals(TypeRequest.HELP_RESPONSE.toString())){
//            HelpResponseRequest helpRequest = new HelpResponseRequest(message, sessionAppli);
//            sessionAppli.getBasicRemote().sendText(message);
//            return helpRequest.getAnswer();
        }
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
