package fr.unice.polytech.PEL.PolyEscape.Server;

import fr.unice.polytech.PEL.PolyEscape.Data.Enigma;
import org.json.JSONObject;

public class Encoder{

    JSONObject json;

    public JSONObject encode(final Enigma enigma){
        json = new JSONObject();
        json.put("description", enigma.getDescription());
        json.put("response", enigma.getResponse());
        return json;
    }

}