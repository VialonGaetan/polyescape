package fr.unice.polytech.pel.polyescape.data;

import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public class Partie implements Serialize {

    protected EscapeGame escapeGame;
    protected int time;
    protected boolean hasStart=false;
    private Joueur joueur;

    protected Partie(EscapeGame escapeGame){
        this.escapeGame = escapeGame;
        this.time = escapeGame.getTime();
    }

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

    protected void attributeEnigme() {}

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

    public void setJoueurReadyOrNot(Joueur joueur) {}

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

    public boolean isFinish(){
        return escapeGame.getEnigmes().stream().allMatch(Enigme::isResolve);
    }

    public boolean checkReponse(Joueur joueur, String reponse){
        if (!getCurrentEnigmesOfaPlayer(joueur).get().checkAnswer(reponse))
            return false;
        sendProgressPlayer();
        return true;
    }

    protected void sendProgressPlayer(){

    }

    @Override
    public JSONObject toJson() {
        return new JSONObject()
                .put(JsonArguments.ESCAPEGAME.toString(),escapeGame.getName())
                .put(JsonArguments.TEMPS.toString(),getTime())
                .put(JsonArguments.EQUIPE.toString(),"");
    }
}
