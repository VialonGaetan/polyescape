package fr.unice.polytech.pel.polyescape.Transmission.requests;

import fr.unice.polytech.pel.polyescape.Data.Enigme;
import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public class EnigmeDispoRequest implements Request {

    private final Gestionnaire gestionnaire = Gestionnaire.getInstance();

    @Override
    public JSONObject getAnswer() {
        JSONArray jsonArray = new JSONArray();
        for (Enigme enigme : gestionnaire.getEnigmeDisponible()) {
            jsonArray.put(enigme.toJson());
        }
        return new JSONObject().put(JsonArguments.ENIGMES.toString(), jsonArray);
    }
}


