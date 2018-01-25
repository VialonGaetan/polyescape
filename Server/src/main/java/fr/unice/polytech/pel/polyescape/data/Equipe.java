package fr.unice.polytech.pel.polyescape.data;

import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import fr.unice.polytech.pel.polyescape.transmission.additionnal.multiplayer.ActualizeWaitingScreenMessage;
import org.json.JSONObject;

import java.util.*;

public class Equipe implements Serialize{

    private final int maxSize = 4;
    private int index = 0;
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

    public Joueur getNextJoueur() {
        List<Joueur> tempList = new ArrayList<>(joueurs.keySet());
        if (index >= joueurs.keySet().size())
            index=0;
        return tempList.get(index++);
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
        return joueurs.values().stream().allMatch(aBoolean -> aBoolean);
    }

    private void actualizeSalon(){
        String message = new ActualizeWaitingScreenMessage(joueurs).createMessageToSend();
        for (Joueur joueur : joueurs.keySet()) {
            joueur.sendMessageToPlayer(message);
        }
    }

    public int getTailleEquipe(){
        return joueurs.keySet().size();
    }


    @Override
    public JSONObject toJson() {

        return new JSONObject().put(JsonArguments.NOM.toString(),this.name);
    }


}
