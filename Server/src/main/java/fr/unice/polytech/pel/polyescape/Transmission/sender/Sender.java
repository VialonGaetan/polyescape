package fr.unice.polytech.pel.polyescape.Transmission.sender;

import org.json.JSONObject;

/**
 * @author Gaetan Vialon
 * Created the 19/01/2018.
 */
public interface Sender {

    default String createMessageToSend(){
        return messageInJson().toString();
    }

    JSONObject messageInJson();
}
