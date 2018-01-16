package fr.unice.polytech.pel.polyescape.Transmission.requests;

import fr.unice.polytech.pel.polyescape.Data.Partie;
import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.logging.Logger;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public class PartieEnCoursListRequest implements Request {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public PartieEnCoursListRequest() {

    }

    @Override
    public String getAnswer() {
        return answerInJson().toString();
    }

    @Override
    public JSONObject answerInJson() {
        JSONArray jsonArray = new JSONArray();
        for (Partie partie : Gestionnaire.getInstance().getPartieEnCours().values()) {
            if (partie.hasStart())
                jsonArray.put(partie.toJson());
        }
        return new JSONObject().put(JsonArguments.PARTIEENCOURS.toString(),jsonArray);
    }
}
