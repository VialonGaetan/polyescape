package fr.unice.polytech.pel.polyescape.Transmission.requests;

import org.json.JSONObject;

import javax.websocket.Session;
import java.util.logging.Logger;

/**
 * @author Gaetan Vialon
 * Created the 16/01/2018.
 */
public class CheckResponseRequest implements Request {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private String message;
    private Session session;

    public CheckResponseRequest() {
        logger.info("Reponse à une question");
    }

    @Override
    public JSONObject getAnswer() {
        return null;
    }
}
