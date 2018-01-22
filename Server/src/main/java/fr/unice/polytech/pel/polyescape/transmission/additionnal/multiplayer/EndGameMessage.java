package fr.unice.polytech.pel.polyescape.transmission.additionnal.multiplayer;

import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import org.json.JSONObject;

/**
 * @author Gaetan Vialon
 * Created the 22/01/2018.
 */
public class EndGameMessage implements MultiPlayerAdditionalMessage {

    private int score;

    public EndGameMessage(int score) {
        this.score = score;
    }

    @Override
    public JSONObject messageInJson() {
        return new JSONObject().put(JsonArguments.REPONSE.toString(), JsonArguments.FINISH.toString())
                .put(JsonArguments.SCORE.toString(), score);
    }
}
