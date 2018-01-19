package fr.unice.polytech.pel.polyescape.Transmission.requests;

import fr.unice.polytech.pel.polyescape.Data.Joueur;
import fr.unice.polytech.pel.polyescape.Gestionnaire;
import javax.websocket.Session;

import fr.unice.polytech.pel.polyescape.Transmission.InvalidJsonRequest;
import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import org.json.JSONObject;

import java.util.logging.Logger;

public class JoinTeamRequest implements Request {

    private int gameId;
    private Joueur player;
    private boolean hasJoin = false;

    public JoinTeamRequest(String message, Session session){
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.info("Connexion à la partie");
        decodeJoinTeam(message,session);
    }

    private void decodeJoinTeam(String message, Session session){
        try {
            JSONObject decode = new JSONObject(message);
            gameId = decode.getInt(JsonArguments.IDPARTIE.toString());
            player = new Joueur(decode.getString(JsonArguments.USERNAME.toString()),session);
            hasJoin = Gestionnaire.getInstance().getPartieByID(gameId).joinPartie(player);
        } catch (Exception e) {
            throw new InvalidJsonRequest();
        }
    }

    @Override
    public JSONObject answerInJson() {
        if(hasJoin){
            return new JSONObject().put(JsonArguments.REPONSE.toString(),JsonArguments.OK.toString());
        }
        else {
            return new JSONObject().put(JsonArguments.REPONSE.toString(),JsonArguments.KO.toString());
        }
    }
}
