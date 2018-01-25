package fr.unice.polytech.pel.polyescape.data;

        import org.json.JSONObject;

        import javax.websocket.Session;

public class GameMaster implements Serialize {

    private final Session session;

    public GameMaster(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    @Override
    public JSONObject toJson() {
        return null;
    }
}
