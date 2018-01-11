package fr.unice.polytech.pel.polyescape.Server;

import fr.unice.polytech.pel.polyescape.Data.Enigma;
import org.json.JSONObject;

public class Decoder {

    public Enigma decode(final JSONObject json){
        Enigma enigma;
        String description = (String)json.get("description");
        String response = (String)json.get("response");
        enigma = new Enigma(description,response);
        return enigma;
    }

}
