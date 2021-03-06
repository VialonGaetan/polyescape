package fr.unice.polytech.pel.polyescape.transmission.additionnal.multiplayer;

import fr.unice.polytech.pel.polyescape.data.Enigme;
import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import org.json.JSONObject;

/**
 * @author Gaetan Vialon
 * Created the 19/01/2018.
 */
public class BeginGameMessage implements MultiPlayerAdditionalMessage {

    private Enigme enigme;
    private int time;

    public BeginGameMessage(Enigme enigme, int time) {
        this.enigme = enigme;
        this.time = time;
    }

    @Override
    public JSONObject messageInJson() {
        return new JSONObject().put(JsonArguments.REPONSE.toString(),JsonArguments.ENIGME.toString())
                .put(JsonArguments.INFOS.toString(),enigme.getDescription())
                .put(JsonArguments.NOM.toString(),enigme.getName())
                .put(JsonArguments.TEMPS.toString(),time);
    }
}
