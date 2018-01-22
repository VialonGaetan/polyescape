package fr.unice.polytech.pel.polyescape.transmission;

import fr.unice.polytech.pel.polyescape.transmission.requests.Request;
import org.json.JSONObject;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public class InvalidJsonRequest extends RuntimeException implements Request{

    private String message;

    public InvalidJsonRequest() {
        super("InvalidJsonRequest");
        message = "InvalidJsonFormat";
    }

    public InvalidJsonRequest(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getAnswer() {
        return message;
    }

    @Override
    public JSONObject answerInJson() {
        return null;
    }
}
