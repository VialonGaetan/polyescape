package fr.unice.polytech.pel.polyescape.Transmission.requests;

import fr.unice.polytech.pel.polyescape.Data.Enigme;
import fr.unice.polytech.pel.polyescape.Data.EscapeGame;
import fr.unice.polytech.pel.polyescape.Gestionnaire;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public class EscapeGameDispoListRequest implements Request {

    private final Gestionnaire gestionnaire = Gestionnaire.getInstance();

    @Override
    public JSONObject getAnswer() {
        JSONArray jsonArray = new JSONArray();
        for (EscapeGame escapeGame : gestionnaire.getEscapeGamesDisponible()) {
            jsonArray.put(escapeGame.toJson());
        }
        return new JSONObject().put("escapesGame",jsonArray);
    }
}
