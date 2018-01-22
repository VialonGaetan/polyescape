package fr.unice.polytech.pel.polyescape.Transmission.requests;

import fr.unice.polytech.pel.polyescape.Data.Joueur;
import fr.unice.polytech.pel.polyescape.Data.Partie;
import fr.unice.polytech.pel.polyescape.Gestionnaire;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Iterator;

public class IndiceRequest implements Request {

    private int idpartie;
    private Partie currentGame;
    private Iterator<Joueur> joueurs;
    private String message;

    public IndiceRequest(String message, Session session) throws IOException {
        idpartie = Integer.valueOf(new JSONObject(message).getString("idGame"));
        currentGame = Gestionnaire.getInstance().getPartieByID(idpartie);
        joueurs = currentGame.getReadyToStart().keySet().iterator();
        this.message = message;
        getAnswer();
    }

    @Override
    public String getAnswer() throws IOException {
        while (joueurs.hasNext()) {
            Joueur j = joueurs.next();
            if (j.getNom().equals(new JSONObject(message).getString("username"))) {
                j.getSession().getBasicRemote().sendText(message);
            }
        }
        return null;
    }

    @Override
    public JSONObject answerInJson() {
       return null;
    }

}
