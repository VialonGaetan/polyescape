package fr.unice.polytech.pel.polyescape.transmission.additionnal.multiplayer;

import fr.unice.polytech.pel.polyescape.data.Enigme;
import fr.unice.polytech.pel.polyescape.data.Joueur;
import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Gaetan Vialon
 * Created the 22/01/2018.
 */
public class ActualizeProgressGameMessage implements MultiPlayerAdditionalMessage {


    private Map<Joueur,List<Enigme>> association;

    public ActualizeProgressGameMessage(Map<Joueur,List<Enigme>> association) {
        this.association = association;
    }

    @Override
    public JSONObject messageInJson() {
        JSONArray jsonArray = new JSONArray();
        for (Joueur joueur : association.keySet()) {
            List<Enigme> enigmes = association.get(joueur);
            Optional<Enigme> currentEnigme = enigmes.stream().filter(enigme -> !enigme.isResolve()).findFirst();
            if (currentEnigme.isPresent())
                jsonArray.put(new JSONObject()
                    .put(JsonArguments.USERNAME.toString(),joueur.getNom())
                    .put(JsonArguments.TOTAL.toString(),enigmes.size())
                    .put(JsonArguments.ACTUAL.toString(),enigmes.indexOf(currentEnigme.get())));
            else
                jsonArray.put(new JSONObject()
                        .put(JsonArguments.USERNAME.toString(),joueur.getNom())
                        .put(JsonArguments.TOTAL.toString(),enigmes.size())
                        .put(JsonArguments.ACTUAL.toString(),enigmes.size()));
        }

        return new JSONObject()
                .put(JsonArguments.REPONSE.toString(),JsonArguments.SUCCESS.toString())
                .put(JsonArguments.JOUEURS.toString(),jsonArray);

    }
}
