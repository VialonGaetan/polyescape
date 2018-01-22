package fr.unice.polytech.pel.polyescape.transmission.requests;

import fr.unice.polytech.pel.polyescape.transmission.InvalidJsonRequest;
import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import fr.unice.polytech.pel.polyescape.transmission.TypeRequest;
import org.json.JSONObject;

import javax.websocket.Session;

/**
 * @author Gaetan Vialon
 * Created the 15/01/2018.
 */
public class RequestFactory {

    public Request createTypeRequest(String message, Session session) {
        switch (TypeRequest.valueOf(new JSONObject(message).getString(JsonArguments.REQUEST.toString()))) {
            case GET_PARTIES:
                return new PartieEnCoursListRequest();
            case GET_ENIGME:
                return new EnigmeDispoRequest();
            case GET_ESCAPE:
                return new EscapeGameDispoListRequest();
            case CREATE_PARTIE:
                return new CreatePartieRequest(message, session);
            case CREATE_ENIGME:
                return new CreateEnigmeRequest();
            case CREATE_ESCAPE:
                return new CreateEscapeRequest();
            case RESPONSE:
                return new CheckResponseRequest(message, session);
            case JOIN_TEAM:
                return new JoinTeamRequest(message,session);
            case GET_SALONS:
                return new SalonListRequest();
            case SET_READY:
                return new SetReadyOrNotRequest(message,session);
            default:
                return new InvalidJsonRequest("InvalidElementRequest");
        }
    }

}
