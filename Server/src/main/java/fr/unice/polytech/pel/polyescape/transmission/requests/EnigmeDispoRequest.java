package fr.unice.polytech.pel.polyescape.transmission.requests;

import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.data.Enigme;
import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.logging.Logger;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public class EnigmeDispoRequest implements Request {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private final Gestionnaire gestionnaire = Gestionnaire.getInstance();

    public EnigmeDispoRequest() {
        logger.info("Recuperation de la liste des enigmes");
    }


    @Override
    public JSONObject answerInJson() {
        JSONArray jsonArray = new JSONArray();
        for (Enigme enigme : gestionnaire.getEnigmeDisponible()) {
            jsonArray.put(enigme.toJson());
        }
        return new JSONObject().put(JsonArguments.ENIGMES.toString(), jsonArray);
    }
}


