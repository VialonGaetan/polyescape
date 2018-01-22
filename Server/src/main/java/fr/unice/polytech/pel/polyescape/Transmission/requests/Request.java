package fr.unice.polytech.pel.polyescape.Transmission.requests;

import org.json.JSONObject;

import java.io.IOException;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public interface Request {

    String getAnswer() throws IOException;

    JSONObject answerInJson();


}
