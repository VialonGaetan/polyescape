package fr.unice.polytech.pel.polyescape.data;

import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
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
    private List<Enigme> enigmes;
    private Joueur joueur;

    protected Partie(EscapeGame escapeGame){
        this.escapeGame = escapeGame;
        this.time = escapeGame.getTime();
    }

    public Partie(EscapeGame escapeGame, Joueur joueur) {
        this.escapeGame = escapeGame;
        this.time = escapeGame.getTime();
        this.joueur = joueur;
        enigmes = new ArrayList<>();
        startTheGame();
    }

    protected void startTheGame(){
        hasStart = true;
        attributeEnigme();
    }


    protected void attributeEnigme() {
        for (Enigme enigme : escapeGame.getEnigmes()) {
            enigmes.add(new Enigme(enigme.name,enigme.description,enigme.reponse));
        }
    }

    public boolean hasStart() {
        return hasStart;
    }

    public List<Enigme> getEnigmesOfaPlayer(Joueur joueur){
        if (this.joueur.equals(joueur))
            return enigmes;
        return new ArrayList<>();
    }

    public Optional<Enigme> getCurrentEnigmesOfaPlayer(Joueur joueur){
        if (this.joueur.equals(joueur))
            return enigmes.stream().filter(enigme -> !enigme.isResolve()).findFirst();
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
        return enigmes.stream().allMatch(Enigme::isResolve);
    }

    public boolean checkReponse(Joueur joueur, String reponse){
        if (getCurrentEnigmesOfaPlayer(joueur).isPresent())
            return getCurrentEnigmesOfaPlayer(joueur).get().checkAnswer(reponse);
        return false;
    }

    public Collection<Joueur> getJoueurs(){
        Collection<Joueur> joueurs = new ArrayList<>();
        joueurs.add(joueur);
        return joueurs;
    }

    @Override
    public JSONObject toJson() {
        return new JSONObject()
                .put(JsonArguments.ESCAPEGAME.toString(),escapeGame.getName())
                .put(JsonArguments.TEMPS.toString(),getTime())
                .put(JsonArguments.JOUEURS.toString(),new JSONArray().put(new JSONObject().put(JsonArguments.USERNAME.toString(),joueur.getNom())));
    }
}