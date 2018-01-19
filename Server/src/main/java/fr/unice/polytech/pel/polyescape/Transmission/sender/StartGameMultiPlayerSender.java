package fr.unice.polytech.pel.polyescape.Transmission.sender;

import fr.unice.polytech.pel.polyescape.Data.Enigme;
import fr.unice.polytech.pel.polyescape.Data.Joueur;
import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import org.json.JSONObject;

/**
 * @author Gaetan Vialon
 * Created the 19/01/2018.
 */
public class StartGameMultiPlayerSender implements Sender {

    private Enigme enigme;

    public StartGameMultiPlayerSender(Enigme enigme) {
        this.enigme = enigme;
    }

    @Override
    public JSONObject messageInJson() {
        return new JSONObject().put(JsonArguments.REPONSE.toString(),JsonArguments.ENIGME.toString())
                .put(JsonArguments.INFOS.toString(),enigme.getDescription())
                .put(JsonArguments.NOM.toString(),enigme.getName());
    }
}
