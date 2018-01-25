package fr.unice.polytech.pel.polyescape.transmission.additionnal.multiplayer;

import fr.unice.polytech.pel.polyescape.data.Indice;
import fr.unice.polytech.pel.polyescape.data.Joueur;
import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * @author Gaetan Vialon
 * Created the 24/01/2018.
 */
public class GiveIndiceMessage implements MultiPlayerAdditionalMessage{
    private Map<Joueur,Indice> indices;

    public GiveIndiceMessage(Map<Joueur,Indice> indices) {
        this.indices = indices;
    }

    @Override
    public JSONObject messageInJson() {
        JSONArray jsonArray = new JSONArray();
        for (Joueur joueur: indices.keySet()) {
            if (indices.get(joueur).isDiscover())
                jsonArray.put(new JSONObject()
                        .put(JsonArguments.USERNAME.toString(),joueur.getNom())
                        .put(JsonArguments.INDICE.toString(),indices.get(joueur).getIndice()));
        }
        return new JSONObject().put(JsonArguments.REPONSE.toString(), JsonArguments.INDICES.toString())
                .put(JsonArguments.INDICES.toString(), jsonArray);
    }
}
