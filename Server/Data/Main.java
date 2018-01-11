package Data;

import Server.Decoder;
import Server.Encoder;
import org.json.simple.JSONObject;

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
