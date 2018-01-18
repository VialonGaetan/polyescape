package fr.unice.polytech.pel.polyescape.Data;

import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class Equipe implements Serialize{

    private String name;
    private Map<Joueur, Boolean> joueurs;

    public Equipe(String name, Joueur joueur){
        this.joueurs = new HashMap<>();
        this.joueurs.put(joueur,false);
        this.name = name;
    }



    public int numberOfPlayer(){
        return joueurs.size();
    }

    public Joueur getRandomJoueur() {
        return joueurs.keySet().stream().findAny().get();
    }


    public boolean joinTeam(Joueur joueur){
        if (joueurs.size()>=4)
            return false;
        joueurs.put(joueur,false);
        return true;
    }

    public String getName(){
        return name;
    }


    public void actualizeSalon(){
        String message = messageToActualizeSalonInJson().toString();
        for (Joueur joueur : joueurs.keySet()) {
            joueur.sendMessageToPlayer(message);
        }
    }

    private JSONObject messageToActualizeSalonInJson(){
        JSONArray jsonArray = new JSONArray();
        for (Joueur joueur : joueurs.keySet()) {
            jsonArray.put(joueur.toJson().put(JsonArguments.READY.toString(),joueurs.get(joueur)));
        }
        return new JSONObject().put(JsonArguments.REPONSE.toString(),JsonArguments.ACTUALISE.toString())
                .put(JsonArguments.JOUEURS.toString(),jsonArray);
    }

    public Collection<Joueur> getJoueurs(){
        return joueurs.keySet();
    }

    public boolean setJoueurReadyOrNot(Joueur joueur, Boolean ready) {
        joueurs.replace(joueur, ready);
        actualizeSalon();
        return !joueurs.values().stream().filter(aBoolean -> !aBoolean).findAny().isPresent();
    }

    @Override
    public JSONObject toJson() {

        return new JSONObject().put(JsonArguments.NOM.toString(),this.name);
    }

}
