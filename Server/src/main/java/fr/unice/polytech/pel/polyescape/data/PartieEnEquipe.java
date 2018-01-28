package fr.unice.polytech.pel.polyescape.data;

import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import fr.unice.polytech.pel.polyescape.transmission.additionnal.multiplayer.ActualizeProgressGameMessage;
import fr.unice.polytech.pel.polyescape.transmission.additionnal.multiplayer.EndGameMessage;
import fr.unice.polytech.pel.polyescape.transmission.additionnal.multiplayer.GiveEnigmeMessage;
import fr.unice.polytech.pel.polyescape.transmission.additionnal.multiplayer.GiveIndiceMessage;
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
    private Map<Joueur, Indice> indiceAssociateToPlayer;

    private FinalEnigme finalEnigme;

    public PartieEnEquipe(EscapeGame escapeGame, Joueur joueur, String teamName) {
        super(escapeGame);
        association = new HashMap<>();
        indiceAssociateToPlayer = new HashMap<>();
        equipe = new Equipe(teamName, joueur);
        FinalEnigme temp = Gestionnaire.getInstance().getRandomFinalEnigme();
        finalEnigme = new FinalEnigme(temp.getName(),temp.getDescription(),temp.getReponse());
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
    public Collection<Joueur> getJoueurs() {
        return equipe.getJoueurs();
    }

    @Override
    public String getTeamName() {
        return equipe.getName();
    }

    @Override
    protected void attributeEnigme() {
        for (Joueur joueur : equipe.getJoueurs()) {
            association.put(joueur, new ArrayList<>());
        }
        Collections.shuffle(escapeGame.getEnigmes());
        for (Enigme enigme : escapeGame.getEnigmes()) {
            association.get(equipe.getNextJoueur()).add(new Enigme(enigme.getName(), enigme.getDescription(), enigme.getReponse()));
        }
        List<String> indice = finalEnigme.getIndiceForXPlayers(equipe.getTailleEquipe());
        int i = 0;
        for (Joueur joueur : association.keySet()) {
            indiceAssociateToPlayer.put(joueur, new Indice(indice.get(i++)));
            if (getCurrentEnigmesOfaPlayer(joueur).isPresent())
                joueur.sendMessageToPlayer(new GiveEnigmeMessage(getCurrentEnigmesOfaPlayer(joueur).get(), time).createMessageToSend());
        }
        sendMessageToAllPlayer(new ActualizeProgressGameMessage(association).createMessageToSend());
    }

    @Override
    public boolean isFinish() {
        sendMessageToAllPlayer(new GiveIndiceMessage(indiceAssociateToPlayer).createMessageToSend());
        for (Joueur joueur : association.keySet()) {
            if (association.get(joueur).stream().anyMatch(enigme -> !enigme.isResolve()))
                return false;
        }
        if (!finalEnigme.isResolve()) {
            for (Joueur joueur : association.keySet()) {
                joueur.sendMessageToPlayer(new GiveEnigmeMessage(finalEnigme, time).createMessageToSend());
            }
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
        if (association.get(joueur).stream().filter(enigme -> !enigme.isResolve()).findFirst().isPresent())
            return association.get(joueur).stream().filter(enigme -> !enigme.isResolve()).findFirst();
        indiceAssociateToPlayer.get(joueur).discoverIndice();
        return Optional.empty();
    }

    @Override
    public void setJoueurReadyOrNot(Joueur joueur) {
        if (equipe.setJoueurReadyOrNot(joueur))
            startTheGame();
    }

    @Override
    public boolean checkReponse(Joueur joueur, String reponse) {
        if (!getCurrentEnigmesOfaPlayer(joueur).isPresent())
            return finalEnigme.checkAnswer(reponse);
        else if (!getCurrentEnigmesOfaPlayer(joueur).get().checkAnswer(reponse))
            return false;
        sendMessageToAllPlayer(new ActualizeProgressGameMessage(association).createMessageToSend());
        return true;
    }


    private void sendMessageToAllPlayer(String message) {
        for (Joueur joueur : association.keySet()) {
            joueur.sendMessageToPlayer(message);
        }
    }

    @Override
    public JSONObject toJson() {
        JSONArray jsonArray = new JSONArray();
        for (Joueur joueur : association.keySet()) {
            JSONArray jsonArrayEnigme = new JSONArray();
            for (Enigme enigme : association.get(joueur)) {
                jsonArrayEnigme.put(enigme.toJson());
            }
            jsonArray.put(joueur.toJson().put(JsonArguments.ENIGMES.toString(), jsonArrayEnigme));
        }

        return new JSONObject().put(JsonArguments.TEMPS.toString(), getTime())
                .put(JsonArguments.JOUEURS.toString(), jsonArray)
                .put(JsonArguments.ESCAPEGAME.toString(), escapeGame.getName());
    }
}
