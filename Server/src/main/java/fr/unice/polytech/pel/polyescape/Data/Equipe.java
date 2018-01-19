package fr.unice.polytech.pel.polyescape.Data;

import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import fr.unice.polytech.pel.polyescape.Transmission.sender.ActualizeSalonSender;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class Equipe implements Serialize{

    private final int maxSize = 4;
    private String name;
    private Map<Joueur, Boolean> joueurs;

    public Equipe(String name, Joueur joueur){
        this.joueurs = new HashMap<>();
        this.joueurs.put(joueur,false);
        this.name = name;
        actualizeSalon();
    }



    public int numberOfPlayer(){
        return joueurs.size();
    }

    public Joueur getRandomJoueur() {
        return joueurs.keySet().stream().findAny().get();
    }


    public boolean joinTeam(Joueur joueur){
        if (joueurs.size()>= maxSize || joueurs.containsKey(joueur))
            return false;
        joueurs.put(joueur,false);
        actualizeSalon();
        return true;
    }

    public String getName(){
        return name;
    }

    public Collection<Joueur> getJoueurs(){
        return joueurs.keySet();
    }

    public boolean setJoueurReadyOrNot(Joueur joueur) {
        joueurs.replace(joueur, !joueurs.get(joueur));
        actualizeSalon();
        return !joueurs.values().stream().filter(aBoolean -> !aBoolean).findAny().isPresent();
    }

    private void actualizeSalon(){
        String message = new ActualizeSalonSender(joueurs).createMessageToSend();
        for (Joueur joueur : joueurs.keySet()) {
            joueur.sendMessageToPlayer(message);
        }
    }


    @Override
    public JSONObject toJson() {

        return new JSONObject().put(JsonArguments.NOM.toString(),this.name);
    }


}
