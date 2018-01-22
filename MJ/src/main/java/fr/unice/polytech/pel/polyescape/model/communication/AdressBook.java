package fr.unice.polytech.pel.polyescape.model.communication;

import javax.websocket.Session;
import java.util.HashMap;

public class AdressBook {

    private static AdressBook adressBook = new AdressBook();
    private Session serverSession;
    private HashMap<String, Session> adressPlayers = new HashMap<>();
    HashMap<String, String> playersEnigma = new HashMap<>();


    public static AdressBook getInstance() {
        return adressBook;
    }

    public Session getServerSession() {
        return serverSession;
    }

    public void setServerSession(Session serverSession) {
        this.serverSession = serverSession;
    }


    public void updateSessions(String playerName, Session hisSession) {
        this.adressPlayers.put(playerName, hisSession);
    }

    public Session getAPlayerSession(String playerName) {
        return this.adressPlayers.get(playerName);
    }

    public void updateEnigma(String nameOfThePlayer, String hisEnigma){
        this.playersEnigma.put(nameOfThePlayer, hisEnigma);
    }

    public HashMap<String, String> getPlayersEnigma() {
        return playersEnigma;
    }


}
