package fr.unice.polytech.pel.polyescape.transmission.requests;

import org.json.JSONObject;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public interface Request {

    default String getAnswer(){
        return answerInJson().toString();
    }

    JSONObject answerInJson();


}
