package fr.unice.polytech.pel.polyescape.Data;

import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public class Joueur implements Serialize{
    private String nom;
    private Session session;

    public Joueur(String nom,Session session) {
        this.nom = nom;
        this.session = session;

    }


    public void sendMessageToPlayer(String message){
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public JSONObject toJson() {
        return null;
    }
}
