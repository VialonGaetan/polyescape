package fr.unice.polytech.pel.polyescape.transmission.additionnal.multiplayer;

import fr.unice.polytech.pel.polyescape.data.Joueur;
import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * @author Gaetan Vialon
 * Created the 19/01/2018.
 */
public class ActualizeWaitingScreenMessage implements MultiPlayerAdditionalMessage {

    private Map<Joueur,Boolean> joueurBooleanMap;

    public ActualizeWaitingScreenMessage(Map<Joueur,Boolean> joueurBooleanMap) {
        this.joueurBooleanMap = joueurBooleanMap;
    }

    @Override
    public JSONObject messageInJson() {
        JSONArray jsonArray = new JSONArray();
        for (Joueur joueur : joueurBooleanMap.keySet()) {
            jsonArray.put(joueur.toJson().put(JsonArguments.READY.toString(),joueurBooleanMap.get(joueur)));
        }
        return new JSONObject().put(JsonArguments.REPONSE.toString(),JsonArguments.ACTUALISE.toString())
                .put(JsonArguments.JOUEURS.toString(),jsonArray);

    }
}
