package fr.unice.polytech.pel.polyescape.Transmission.requests;

import fr.unice.polytech.pel.polyescape.Transmission.InvalidJsonRequest;
import fr.unice.polytech.pel.polyescape.Transmission.TypeRequest;
import org.json.JSONObject;

import javax.websocket.Session;

/**
 * @author Gaetan Vialon
 * Created the 15/01/2018.
 */
public class RequestFactory {

    public Request createTypeRequest(String message, Session session) {
        switch (TypeRequest.valueOf(new JSONObject(message).getString("request"))) {
            case GET_PARTIES:
                return new PartieEnCoursListRequest();
            case GET_ENIGME:
                return new EnigmeDispoRequest();
            case GET_ESCAPE:
                return new EscapeGameDispoListRequest();
            case CREATE_ENIGME:
                return new CreatePartieRequest(message,session);
            default:
                throw new InvalidJsonRequest();
        }
    }

}