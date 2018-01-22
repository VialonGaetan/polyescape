package fr.unice.polytech.pel.polyescape.model.communication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONObject;

public class DataParser {

    private String answer;
    private int timeInMinutes;
    ObservableList<String> players = FXCollections.observableArrayList();
    private String escapeName = "";

    public DataParser(String answer) {
        this.answer = answer;
        this.timeInMinutes=0;
    }

    public int getTime(){
//        System.out.println(answer);
//        System.out.println("parsing time :\n");
        JSONObject jsonObject = new JSONObject(answer);
        this.timeInMinutes=Integer.valueOf(jsonObject.getJSONArray("partieencours").getJSONObject(0).get("temps").toString());
       /* System.out.println("time aff : "+jsonObject.getJSONArray("partieencours").getJSONObject(0).get("temps"));
        System.out.println("time : "+this.timeInMinutes);
        System.out.println("taille : "+this.players.size());
        for (int i =0; i<this.players.size(); i++){
            System.out.println(this.players.get(i));
        }*/
        return this.timeInMinutes;
    }

    public ObservableList<String> getPlayers() {
        JSONObject jsonObject = new JSONObject(answer);
        for (int i=0; i<jsonObject.getJSONArray("partieencours").getJSONObject(0).getJSONArray("joueurs").length(); i++){
            players.add(jsonObject.getJSONArray("partieencours").getJSONObject(0).getJSONArray("joueurs").getJSONObject(0).get("nom").toString());
        }

        return this.players;

    }

    public String getTeamName(){
        System.out.println("parsing time :\n");
        JSONObject jsonObjectTeamName = new JSONObject(answer);
        this.escapeName=jsonObjectTeamName.getJSONArray("partieencours").getJSONObject(0).get("escapegame").toString();
        return this.escapeName;
    }
}
