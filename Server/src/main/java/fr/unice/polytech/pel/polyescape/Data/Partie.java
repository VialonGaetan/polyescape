package fr.unice.polytech.pel.polyescape.Data;

import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import org.json.JSONObject;

import java.util.*;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public class Partie implements Serialize {

    private EscapeGame escapeGame;
    private Equipe equipe;
    private int time;
    private boolean hasStart=false;

    public Partie(EscapeGame escapeGame, Joueur joueur, TypePartie typePartie) {
        this.escapeGame = escapeGame;
        this.time = escapeGame.getTime();
        equipe = new Equipe("",joueur);
    }

    public Partie(EscapeGame escapeGame, Joueur joueur, TypePartie typePartie, String teamName) {
        this.equipe = new Equipe(teamName,joueur);
        this.escapeGame = escapeGame;
        this.time = escapeGame.getTime();
    }

    private void startTheGame(){
        hasStart = true;
        attributeEnigme();
    }

    private void attributeEnigme() {
        equipe.attributeEnigme(escapeGame);
    }

    public boolean hasStart() {
        return hasStart;
    }

    public List<Enigme> getEnigmesOfaPlayer(Joueur joueur){
        return equipe.getEnigmesOfaPlayer(joueur);
    }

    public Optional<Enigme> getCurrentEnigmesOfaPlayer(Joueur joueur){
        return equipe.getCurrentEnigmesOfaPlayer(joueur);
    }

    public int getTime() {
        return time;
    }

    public boolean joinPartie(Joueur joueur){
        if (hasStart)
            return false;
        else {
            return equipe.joinPartie(joueur);
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JsonArguments.ESCAPEGAME.toString(),escapeGame.getName());
        jsonObject.put(JsonArguments.TEMPS.toString(),getTime());
        jsonObject.put(JsonArguments.EQUIPE.toString(),equipe.toJson());
        return jsonObject;
    }
}
