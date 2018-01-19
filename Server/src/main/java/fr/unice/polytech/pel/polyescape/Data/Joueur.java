package fr.unice.polytech.pel.polyescape.Data;

import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public class Joueur implements Serialize {
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
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendEnigmeToPlayer(Enigme enigme) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JsonArguments.NOM.toString(),enigme.getName())
                .put(JsonArguments.INFOS.toString(),enigme.getDescription());
        try {
            session.getBasicRemote().sendText(jsonObject.toString());
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
            return joueur.nom != null && this.nom != null && this.session != null && joueur.session != null &&
                    this.nom.equals(joueur.nom) && this.session.getId().equals(joueur.session.getId());
        }
    }

    @Override
    public int hashCode() {
        int result = nom != null ? nom.hashCode() : 0;
        result = 31 * result + (session != null ? session.hashCode() : 0);
        return result;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }
}
