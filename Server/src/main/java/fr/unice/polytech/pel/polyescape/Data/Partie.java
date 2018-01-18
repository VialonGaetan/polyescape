package fr.unice.polytech.pel.polyescape.Data;

import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import org.json.JSONObject;

import java.util.*;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public class Partie implements Serialize {

    protected EscapeGame escapeGame;
    private int time;
    protected boolean hasStart=false;
    private Joueur joueur;

    protected Partie(){}

    public Partie(EscapeGame escapeGame, Joueur joueur) {
        this.escapeGame = escapeGame;
        this.time = escapeGame.getTime();
        this.joueur = joueur;
        startTheGame();
    }

    protected void startTheGame(){
        hasStart = true;
        attributeEnigme();
    }

    protected void attributeEnigme() {
    }

    public boolean hasStart() {
        return hasStart;
    }

    public List<Enigme> getEnigmesOfaPlayer(Joueur joueur){
        if (this.joueur.equals(joueur))
            return escapeGame.getEnigmes();
        return new ArrayList<>();
    }

    public Optional<Enigme> getCurrentEnigmesOfaPlayer(Joueur joueur){
        if (this.joueur.equals(joueur))
            return escapeGame.getEnigmes().stream().filter(enigme -> !enigme.isResolve()).findFirst();
        else return Optional.empty();
    }

    public boolean joinPartie(Joueur joueur){
        return false;
    }

    public void setJoueurReadyOrNot(Joueur joueur, Boolean ready){
    }

    public String getEscapeGameName(){
        return escapeGame.getName();
    }

    public int getTime() {
        return time;
    }

    public int numberOfPlayer(){
        return 1;
    }

    public String getTeamName(){
        return "";
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JsonArguments.ESCAPEGAME.toString(),escapeGame.getName());
        jsonObject.put(JsonArguments.TEMPS.toString(),getTime());
        jsonObject.put(JsonArguments.EQUIPE.toString(),"");
        return jsonObject;
    }
}
