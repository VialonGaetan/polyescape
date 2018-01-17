package fr.unice.polytech.pel.polyescape.Transmission.requests;

import fr.unice.polytech.pel.polyescape.Data.EscapeGame;
import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.logging.Logger;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public class EscapeGameDispoListRequest implements Request {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private final Gestionnaire gestionnaire = Gestionnaire.getInstance();

    public EscapeGameDispoListRequest() {
        logger.info("Recuperation de la liste des escape game");
    }

    @Override
    public String getAnswer() {
        return answerInJson().toString();
    }

    @Override
    public JSONObject answerInJson() {
        JSONArray jsonArray = new JSONArray();
        for (EscapeGame escapeGame : gestionnaire.getEscapeGamesDisponible()) {
            jsonArray.put(escapeGame.toJson());
        }
        return new JSONObject().put(JsonArguments.ESCAPEGAMES.toString(),jsonArray);
    }
}
