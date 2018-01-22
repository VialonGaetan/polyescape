package fr.unice.polytech.pel.polyescape.transmission.requests;

import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.data.Joueur;
import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import org.json.JSONObject;

import javax.websocket.Session;
import java.util.logging.Logger;

/**
 * @author Gaetan Vialon
 * Created the 19/01/2018.
 */
public class SetReadyOrNotRequest implements Request {

    private Gestionnaire gestionnaire = Gestionnaire.getInstance();
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private Joueur joueur;
    private int partieID;

    public SetReadyOrNotRequest(String message, Session session) {
        JSONObject decode = new JSONObject(message);
        partieID = decode.getInt(JsonArguments.IDPARTIE.toString());
        joueur = new Joueur(decode.getString(JsonArguments.USERNAME.toString()),session);
        logger.info("Set joueur" + joueur.getNom() + "Ready or NOt");
        gestionnaire.getPartieByID(partieID).setJoueurReadyOrNot(joueur);
    }

    @Override
    public JSONObject answerInJson() {
        return new JSONObject().put(JsonArguments.REPONSE.toString(),JsonArguments.OK.toString());
    }
}
