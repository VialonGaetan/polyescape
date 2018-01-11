package Server;

import Data.Enigma;
import org.json.simple.JSONObject;

public class Encoder{

    JSONObject json;

    public JSONObject encode(final Enigma enigma){
        json = new JSONObject();
        json.put("description", enigma.getDescription());
        json.put("response", enigma.getResponse());
        return json;
    }

}