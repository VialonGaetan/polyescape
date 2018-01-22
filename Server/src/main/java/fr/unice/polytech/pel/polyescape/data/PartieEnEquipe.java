package fr.unice.polytech.pel.polyescape.data;

import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import fr.unice.polytech.pel.polyescape.transmission.additionnal.multiplayer.ActualizeProgressGameMessage;
import fr.unice.polytech.pel.polyescape.transmission.additionnal.multiplayer.BeginGameMessage;
import fr.unice.polytech.pel.polyescape.transmission.additionnal.multiplayer.EndGameMessage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * @author Gaetan Vialon
 * Created the 18/01/2018.
 */
public class PartieEnEquipe extends Partie implements Serialize {

    private Equipe equipe;
    private Map<Joueur, List<Enigme>> association;

    public PartieEnEquipe(EscapeGame escapeGame, Joueur joueur, String teamName) {
        super(escapeGame);
        association = new HashMap<>();
        equipe = new Equipe(teamName, joueur);
    }

    @Override
    public int numberOfPlayer() {
        return equipe.numberOfPlayer();
    }

    @Override
    public boolean joinPartie(Joueur joueur) {
        return !hasStart && equipe.joinTeam(joueur);

    }

    @Override
    public String getTeamName() {
        return equipe.getName();
    }

    @Override
    protected void attributeEnigme(){
        for (Joueur joueur: equipe.getJoueurs()) {
            association.put(joueur, new ArrayList<>());
        }
        Collections.shuffle(escapeGame.getEnigmes());
        for (Enigme enigme : escapeGame.getEnigmes()) {
            association.get(equipe.getNextJoueur()).add(new Enigme(enigme.getName(),enigme.getDescription(),enigme.getReponse()));
        }
        for (Joueur joueur : association.keySet()) {
            joueur.sendMessageToPlayer(new BeginGameMessage(getCurrentEnigmesOfaPlayer(joueur).get(),time).createMessageToSend());
        }
        sendMessageToAllPlayer(new ActualizeProgressGameMessage(association).createMessageToSend());
    }

    @Override
    public boolean isFinish() {
        for (Joueur joueur : association.keySet()) {
            if (association.get(joueur).stream().anyMatch(enigme -> !enigme.isResolve()))
                return false;
        }
        sendMessageToAllPlayer(new EndGameMessage(100).createMessageToSend());
        return true;
    }

    @Override
    public List<Enigme> getEnigmesOfaPlayer(Joueur joueur) {
        return association.get(joueur);
    }

    @Override
    public Optional<Enigme> getCurrentEnigmesOfaPlayer(Joueur joueur) {
        return association.get(joueur).stream().filter(enigme -> !enigme.isResolve()).findFirst();
    }

    @Override
    public void setJoueurReadyOrNot(Joueur joueur) {
        if (equipe.setJoueurReadyOrNot(joueur))
            startTheGame();
    }

    @Override
    protected void sendProgressPlayer(){
        sendMessageToAllPlayer(new ActualizeProgressGameMessage(association).createMessageToSend());
    }

    private void sendMessageToAllPlayer(String message){
        for (Joueur joueur : association.keySet()) {
            joueur.sendMessageToPlayer(message);
        }
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
