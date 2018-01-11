package fr.unice.polytech.pel.polyescape;

import fr.unice.polytech.pel.polyescape.Data.Enigma;
import fr.unice.polytech.pel.polyescape.Server.Decoder;
import fr.unice.polytech.pel.polyescape.Server.Encoder;
import org.json.JSONObject;

/**
 * @author Gaetan Vialon
 * Created the 11/01/2018.
 */
public class Main {
    public static void main(String[] args) {
        Encoder encoder = new Encoder();
        Decoder decoder = new Decoder();
        Enigma enigma = new Enigma("ma description", "ma reponse");
        JSONObject jsonObject = encoder.encode(enigma);
        System.out.println("ok encode\n");
        Enigma returnedEnigma = decoder.decode(jsonObject);
        System.out.println(returnedEnigma.getDescription());
        System.out.println(returnedEnigma.getResponse());
        System.out.println("ok");
    }
}
