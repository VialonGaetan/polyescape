package fr.unice.polytech.pel.polyescape.Data;

import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public class Joueur implements Serialize {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private String nom;
    private Session session;

    public Joueur(String nom, Session session) {
        this.nom = nom;
        this.session = session;

    }

    public String getNom() {
        return nom;
    }

    public void sendMessageToPlayer(String message) {
        try {
            if (session != null) {
                session.getBasicRemote().sendText(message);
                logger.info("Send message : " + message + " to " + session);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject toJson() {
        return new JSONObject().put(JsonArguments.NOM.toString(),this.nom);
    }

    @Override
    public boolean equals(Object o) {
        Joueur joueur = (Joueur) o;
        if (this.session == null || joueur.session == null)//Uniquement pour faire des test
            return this.nom.equals(joueur.nom);
        else {
            return joueur.nom != null && this.nom != null && this.session != null && this.nom.equals(joueur.nom) && this.session.getId().equals(joueur.session.getId());
        }
    }

    @Override
    public int hashCode() {
        int result = nom != null ? nom.hashCode() : 0;
        result = 31 * result + (session != null ? session.hashCode() : 0);
        return result;
    }
}
