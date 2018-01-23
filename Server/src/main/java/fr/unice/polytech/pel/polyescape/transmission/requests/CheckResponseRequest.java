package fr.unice.polytech.pel.polyescape.transmission.requests;

import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.data.Joueur;
import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import org.json.JSONObject;

import javax.websocket.Session;
import java.util.logging.Logger;

/**
 * @author Gaetan Vialon
 * Created the 16/01/2018.
 */
public class CheckResponseRequest implements Request {

    private Gestionnaire gestionnaire = Gestionnaire.getInstance();
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private Boolean isGood;
    private Joueur joueur;
    private int partieID;

    public CheckResponseRequest(String message, Session session) {
        logger.info("Reponse à une question");
        JSONObject decode = new JSONObject(message);
        partieID = decode.getInt(JsonArguments.IDPARTIE.toString());
        joueur = new Joueur(decode.getString(JsonArguments.USERNAME.toString()), session);
        isGood = checkReponse(decode.getString(JsonArguments.REPONSE.toString()));

    }

    private boolean checkReponse(String reponse) {
        return gestionnaire.getPartieByID(partieID).checkReponse(joueur,reponse);
    }

    @Override
    public String getAnswer() {
        return answerInJson().toString();
    }

    @Override
    public JSONObject answerInJson() {
        if (isGood) {
            if (gestionnaire.getPartieByID(partieID).getCurrentEnigmesOfaPlayer(joueur).isPresent())
                return new JSONObject().put(JsonArguments.REPONSE.toString(), JsonArguments.OK.toString())
                        .put(JsonArguments.NOM.toString(), gestionnaire.getPartieByID(partieID).getCurrentEnigmesOfaPlayer(joueur).get().getName())
                        .put(JsonArguments.INFOS.toString(), gestionnaire.getPartieByID(partieID).getCurrentEnigmesOfaPlayer(joueur).get().getDescription());
                return new JSONObject().put(JsonArguments.REPONSE.toString(), JsonArguments.FINISH.toString())
                        .put(JsonArguments.SCORE.toString(), 100);
        } else
            return new JSONObject().put(JsonArguments.REPONSE.toString(), JsonArguments.KO.toString());
    }
}
