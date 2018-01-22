package fr.unice.polytech.pel.polyescape.Transmission.requests;

import fr.unice.polytech.pel.polyescape.Data.Joueur;
import fr.unice.polytech.pel.polyescape.Data.GameMaster;
import fr.unice.polytech.pel.polyescape.Data.Partie;
import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

public class HelpRequest implements Request {

    private Gestionnaire gestionnaire = Gestionnaire.getInstance();
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private Joueur joueur;
    private GameMaster gameMaster;
    private int partieID;
    private String message;

    public HelpRequest(String message, Session session) throws IOException {
        logger.info("Demande d'aide");
        this.message = message;
        int idpartie = new JSONObject(message).getInt("idpartie");
        Partie currentGame = Gestionnaire.getInstance().getPartieByID(idpartie);
        Iterator<Joueur> joueurs = currentGame.getReadyToStart().keySet().iterator();
        while (joueurs.hasNext()) {
            Joueur j = joueurs.next();
            if (j.getNom().equals(new JSONObject(message).getString("username"))) {
                j.setSession(session);
            }
        }
        Gestionnaire.getInstance().getSessionMG().getBasicRemote().sendText(getAnswer());
    }

    @Override
    public String getAnswer() {
        return answerInJson().toString();
    }

    @Override
    public JSONObject answerInJson() {
        System.out.println("debut");
        System.out.println(message);
        JSONObject tmpJson = new JSONObject(message);
        JSONObject jsonObject = new JSONObject().put("reponse",tmpJson.getString(JsonArguments.REQUEST.toString()));
        jsonObject.put("username",tmpJson.getString("username"));
        jsonObject.put("enigme", tmpJson.getString("enigme"));
        jsonObject.put("idGame",tmpJson.getInt("idpartie"));
        return jsonObject;
    }
}
