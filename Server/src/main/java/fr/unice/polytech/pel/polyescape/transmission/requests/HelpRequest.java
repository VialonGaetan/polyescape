package fr.unice.polytech.pel.polyescape.transmission.requests;

import fr.unice.polytech.pel.polyescape.data.Joueur;
import fr.unice.polytech.pel.polyescape.data.GameMaster;
import fr.unice.polytech.pel.polyescape.data.Partie;
import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;

public class HelpRequest implements Request {

    private Gestionnaire gestionnaire = Gestionnaire.getInstance();
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private Joueur joueur;
    private GameMaster gameMaster;
    private int partieID;
    private boolean mjPresent;
    private String message;

    public HelpRequest(String message, Session session) {
        logger.info("Demande d'aide");
        this.message = message;
        int idpartie = new JSONObject(message).getInt("idpartie");
        Partie currentGame = Gestionnaire.getInstance().getPartieByID(idpartie);
        mjPresent = sendInfoToMJ();
    }

    public Boolean sendInfoToMJ() {
        JSONObject tmpJson = new JSONObject(message);
        JSONObject jsonObject = new JSONObject().put("reponse", tmpJson.getString(JsonArguments.REQUEST.toString()));
        jsonObject.put("username", tmpJson.getString("username"));
        jsonObject.put("enigme", tmpJson.getString("enigme"));
        jsonObject.put("idGame", tmpJson.getInt("idpartie"));
        try {
            Session sessionMJ = Gestionnaire.getInstance().getSessionMG();
            if (sessionMJ != null && sessionMJ.isOpen()){
                sessionMJ.getBasicRemote().sendText(jsonObject.toString());
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getAnswer() {
        return answerInJson().toString();
    }

    @Override
    public JSONObject answerInJson() {
        if (mjPresent)
            return new JSONObject().put(JsonArguments.REPONSE.toString(), JsonArguments.OK.toString());
        return new JSONObject().put(JsonArguments.REPONSE.toString(), JsonArguments.KO.toString());
    }
}
