package fr.unice.polytech.pel.polyescape.transmission.additionnal.multiplayer;

import org.json.JSONObject;

/**
 * @author Gaetan Vialon
 * Created the 19/01/2018.
 */
public interface MultiPlayerAdditionalMessage {

    default String createMessageToSend(){
        return messageInJson().toString();
    }

    JSONObject messageInJson();
}
