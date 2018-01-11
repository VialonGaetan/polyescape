package Server;

import Data.Enigma;
import org.json.simple.JSONObject;

public class Decoder {

    public Enigma decode(final JSONObject json){
        Enigma enigma;
        String description = (String)json.get("description");
        String response = (String)json.get("response");
        enigma = new Enigma(description,response);
        return enigma;
    }

}
