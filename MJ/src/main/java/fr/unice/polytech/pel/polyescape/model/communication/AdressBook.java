package fr.unice.polytech.pel.polyescape.model.communication;

import javax.websocket.Session;

public class AdressBook {

    private static AdressBook adressBook = new AdressBook();
    private Session serverSession;

    public static AdressBook getInstance() {
        return adressBook;
    }

    public Session getServerSession() {
        return serverSession;
    }

    public void setServerSession(Session serverSession) {
        this.serverSession = serverSession;
    }
}
