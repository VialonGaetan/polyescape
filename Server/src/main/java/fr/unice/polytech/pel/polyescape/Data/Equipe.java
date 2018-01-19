package fr.unice.polytech.pel.polyescape.Data;

import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import fr.unice.polytech.pel.polyescape.Transmission.sender.ActualizeSalonSender;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
        Iterator<Joueur> iterator = joueurs.keySet().iterator();
        if (index >= joueurs.keySet().size())
            index=0;
        for (int i = 0; i < index ; i++) {
            iterator.next();
        }
        index++;
        return iterator.next();
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
