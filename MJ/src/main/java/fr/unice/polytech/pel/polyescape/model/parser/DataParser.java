package fr.unice.polytech.pel.polyescape.model.parser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONObject;

public class DataParser {

    private String answer;
    private int timeInMinutes;
    ObservableList<String> players = FXCollections.observableArrayList();
    private String escapeName = "";
    private int cpt=0;

    public DataParser(String answer) {
        this.answer = answer;
        this.timeInMinutes=0;
    }

    public int getTime(){
        JSONObject jsonObject = new JSONObject(answer);
        this.timeInMinutes=Integer.valueOf(jsonObject.getJSONArray("partieencours").getJSONObject(0).get("temps").toString());
        return this.timeInMinutes;
    }

    public ObservableList<String> getPlayers() {
        JSONObject jsonObject = new JSONObject(answer);
        for (int i=0; i<jsonObject.getJSONArray("partieencours").getJSONObject(0).getJSONArray("joueurs").length(); i++){
            players.add(jsonObject.getJSONArray("partieencours").getJSONObject(0).getJSONArray("joueurs").getJSONObject(0).get("username").toString());
        }

        return this.players;

    }

    public String getTeamName(){
        JSONObject jsonObjectTeamName = new JSONObject(answer);
        this.escapeName=jsonObjectTeamName.getJSONArray("partieencours").getJSONObject(0).get("escapegame").toString();
        return this.escapeName;
    }

    public double getProgress(){
        JSONObject jsonObject = new JSONObject(answer);
        int length = jsonObject.getJSONArray("partieencours").getJSONObject(0).getJSONArray("joueurs").getJSONObject(0).getJSONArray("enigmes").length();
        for (int i=0; i<length; i++){
            if(jsonObject.getJSONArray("partieencours").getJSONObject(0).getJSONArray("joueurs").getJSONObject(0).getJSONArray("enigmes").getJSONObject(i).getBoolean("isresolve")){
                cpt++;
            }
        }
        double progress = (double)(cpt) / (double)(length);
        return progress;
    }
}
