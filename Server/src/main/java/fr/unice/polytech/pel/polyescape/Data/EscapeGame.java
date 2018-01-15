package fr.unice.polytech.pel.polyescape.Data;

import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public class EscapeGame implements Serialize {

    private String name;
    private List<Enigme> enigmes;
    private int time;

    public EscapeGame(String name,List<Enigme> enigmes, int time) {
        this.name = name;
        this.enigmes = enigmes;
        this.time = time;
    }


    public String getName() {
        return name;
    }

    public List<Enigme> getEnigmes() {
        return enigmes;
    }

    public int getTime() {
        return time;
    }

    @Override
    public JSONObject toJson() {
        JSONArray jsonArray = new JSONArray();
        for (Enigme enigme: enigmes) {
            jsonArray.put(enigme.toJson());
        }

        return new JSONObject().put(JsonArguments.NOM.toString(),name)
                .put(JsonArguments.TEMPS.toString(),time)
                .put(JsonArguments.ENIGMES.toString(),jsonArray);
    }
}
