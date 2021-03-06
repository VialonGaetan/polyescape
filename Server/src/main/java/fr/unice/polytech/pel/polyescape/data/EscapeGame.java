package fr.unice.polytech.pel.polyescape.data;

import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
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
    private String description;
    private int time;

    public EscapeGame(String name,String description,List<Enigme> enigmes, int time) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    @Override
    public JSONObject toJson() {
        JSONArray jsonArray = new JSONArray();
        for (Enigme enigme: enigmes) {
            jsonArray.put(enigme.toJson());
        }

        return new JSONObject().put(JsonArguments.NOM.toString(),name)
                .put(JsonArguments.TEMPS.toString(),time)
                .put(JsonArguments.INFOS.toString(),description)
                .put(JsonArguments.ENIGMES.toString(),jsonArray);
    }
}
