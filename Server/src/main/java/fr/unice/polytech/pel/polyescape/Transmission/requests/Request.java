package fr.unice.polytech.pel.polyescape.Transmission.requests;

import org.json.JSONObject;

import javax.websocket.Session;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public interface Request {

    JSONObject getAnswer();


}
