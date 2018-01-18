package fr.unice.polytech.pel.polyescape.Transmission.requests;

import fr.unice.polytech.pel.polyescape.Data.Joueur;
import fr.unice.polytech.pel.polyescape.Data.Partie;
import fr.unice.polytech.pel.polyescape.Data.PartieEnEquipe;
import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Gaetan Vialon
 * Created the 18/01/2018.
 */
public class SalonListRequest implements Request {


    public SalonListRequest() {
    }

    @Override
    public String getAnswer() {
        return answerInJson().toString();
    }

    @Override
    public JSONObject answerInJson() {
        Gestionnaire gestionnaire = Gestionnaire.getInstance();
        JSONArray jsonArray = new JSONArray();
        for (Integer id :gestionnaire.getParties().keySet()) {
            if (!gestionnaire.getPartieByID(id).hasStart()) {
                Partie partie = gestionnaire.getPartieByID(id);
                jsonArray.put(new JSONObject().put(JsonArguments.IDPARTIE.toString(),id)
                        .put(JsonArguments.ESCAPEGAME.toString(),partie.getEscapeGameName())
                        .put(JsonArguments.JOUEURS.toString(),partie.numberOfPlayer())
                        .put(JsonArguments.EQUIPE.toString(),partie.getTeamName()));

            }

        }
        return new JSONObject().put(JsonArguments.PARTIEATTENTE.toString(),jsonArray);
    }
}
