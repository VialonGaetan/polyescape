package fr.unice.polytech.pel.polyescape.transmission.requests;

import fr.unice.polytech.pel.polyescape.data.Joueur;
import fr.unice.polytech.pel.polyescape.data.Partie;
import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

public class IndiceRequest implements Request {

    private int idpartie;
    private Partie currentGame;
    private Collection<Joueur> joueurs;
    private String message;

    public IndiceRequest(String message, Session session){
        System.out.println(message);
        idpartie = Integer.valueOf(new JSONObject(message).getString("idGame"));
        currentGame = Gestionnaire.getInstance().getPartieByID(idpartie);
        joueurs = currentGame.getJoueurs();
        this.message = message;
        sendIndiceToPlayer();
    }

    public void sendIndiceToPlayer(){
        Optional<Joueur> joueur = joueurs.stream().filter(joueur1 -> joueur1.getNom().equals(new JSONObject(message).getString("username"))).findFirst();
        if (joueur.isPresent())
            joueur.get().sendMessageToPlayer(message);
    }

    @Override
    public String getAnswer() {
        return answerInJson().toString();
    }

    @Override
    public JSONObject answerInJson() {
       return new JSONObject().put(JsonArguments.REPONSE.toString(),JsonArguments.OK.toString());
    }

}
