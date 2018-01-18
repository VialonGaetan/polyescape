package fr.unice.polytech.pel.polyescape.Transmission.requests;

import fr.unice.polytech.pel.polyescape.Data.Joueur;
import fr.unice.polytech.pel.polyescape.Data.GameMaster;
import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import org.json.JSONObject;

import javax.websocket.Session;
import java.util.logging.Logger;

public class HelpRequest implements Request {

    private Gestionnaire gestionnaire = Gestionnaire.getInstance();
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private Joueur joueur;
    private GameMaster gameMaster;
    private int partieID;
    private String message;

    public HelpRequest(String message, Session session) {
        logger.info("Demande d'aide");
//        JSONObject decode = new JSONObject(message);
//        partieID = decode.getInt(JsonArguments.IDPARTIE.toString());
        this.message = message;
    }

    @Override
    public String getAnswer() {
        return answerInJson().toString();
    }

    @Override
    public JSONObject answerInJson() {
        return new JSONObject(message);
    }
}