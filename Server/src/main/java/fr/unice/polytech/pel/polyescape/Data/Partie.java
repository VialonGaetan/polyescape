package fr.unice.polytech.pel.polyescape.Data;

import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public class Partie implements Serialize {

    private EscapeGame escapeGame;
    private Map<Joueur, List<Enigme>> association;
    private Map<Joueur, Boolean> readyToStart;
    private int time;
    private int id;
    private boolean hasStart=false;

    public Partie(EscapeGame escapeGame, Joueur joueur, TypePartie typePartie) {
        this.escapeGame = escapeGame;
        this.time = escapeGame.getTime();
        readyToStart = new HashMap<>();
        association = new HashMap<>();
        if (typePartie.equals(TypePartie.SOLO)){
            readyToStart.put(joueur,true);
            startTheGame();
        }
        else{
            readyToStart.put(joueur,false);
        }
    }

    private void startTheGame(){
        hasStart = true;
        for (Joueur joueur : readyToStart.keySet()) {
            association.put(joueur,new ArrayList<>());
        }
        attributeEnigme();
    }

    private void attributeEnigme() {
        for (Enigme enigme : escapeGame.getEnigmes()) {
            association.get(getRandomJoueur(association.keySet())).add(new Enigme(enigme.getName(),enigme.getDescription(),enigme.getReponse()));
        }
    }

    private Joueur getRandomJoueur(Set<Joueur> joueurs) {
        return joueurs.stream().findFirst().get();
    }

    public boolean hasStart() {
        return hasStart;
    }

    public List<Enigme> getEnigmesOfaPlayer(Joueur joueur){
        return association.get(joueur);
    }

    public Optional<Enigme> getCurrentEnigmesOfaPlayer(Joueur joueur){
        return association.get(joueur).stream().filter(enigme -> enigme.isResolve()==false).findFirst();
    }

    public boolean joinPartie(Joueur joueur){
        if (hasStart)
            return false;
        else if (readyToStart.keySet().size()>=4)
            return false;
        else {
            return readyToStart.put(joueur,false);
        }

    }
    public int getTime() {
        return time;
    }

    @Override
    public JSONObject toJson() {
        JSONArray jsonArray = new JSONArray();
        for (Joueur joueur: association.keySet()) {
            JSONArray jsonArrayEnigme = new JSONArray();
            for (Enigme enigme : association.get(joueur)) {
                jsonArrayEnigme.put(enigme.toJson());
            }
            jsonArray.put(joueur.toJson().put(JsonArguments.ENIGMES.toString(),jsonArrayEnigme));
        }

        return new JSONObject().put(JsonArguments.TEMPS.toString(),getTime())
                    .put(JsonArguments.JOUEURS.toString(),jsonArray)
                    .put(JsonArguments.ESCAPEGAME.toString(),escapeGame.getName());
    }
}
