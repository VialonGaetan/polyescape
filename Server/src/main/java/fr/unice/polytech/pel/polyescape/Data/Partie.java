package fr.unice.polytech.pel.polyescape.Data;

import fr.unice.polytech.pel.polyescape.Gestionnaire;
import org.glassfish.tyrus.client.ClientManager;
import org.json.JSONObject;

import javax.websocket.DeploymentException;
import javax.websocket.Session;
import java.net.URISyntaxException;
import java.util.*;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public class Partie implements Serialize {

    private EscapeGame escapeGame;
    private Map<Joueur, List<Enigme>> lol;
    private Map<Joueur, Boolean> readyToStart;
    private int time;
    private int id;

    public Partie(EscapeGame escapeGame, Joueur joueur) {
        this.escapeGame = escapeGame;
        this.time = escapeGame.getTime();
        readyToStart = new HashMap<>();
        lol = new HashMap<>();
        readyToStart.put(joueur,false);
    }

    public List<Enigme> getListEnigmesForOnePlayer(Joueur joueur){
        return lol.get(joueur);
    }

    public void setSomeOneReadyOrNot(Joueur joueur, boolean ready){
        readyToStart.put(joueur,ready);
        readyToStart.forEach((player, isReady) -> {
            if (isReady == false)
                return;
        });
        startTheGame();

    }

    private void attributeEnigme() {
        for (Enigme enigme : escapeGame.getEnigmes()) {
            lol.get(getRandomJoueur(readyToStart.keySet())).add(enigme);
        }
    }

    private void startTheGame(){
        for (Joueur joueur : readyToStart.keySet()) {
            lol.put(joueur,new ArrayList<>());
        }
        attributeEnigme();
    }

    @Override
    public JSONObject toJson() {
        return null;
    }

    private Joueur getRandomJoueur(Set<Joueur> joueurs) {
        return joueurs.stream().findFirst().get();
    }

}
