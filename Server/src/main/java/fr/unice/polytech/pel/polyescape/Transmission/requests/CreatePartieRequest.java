package fr.unice.polytech.pel.polyescape.Transmission.requests;

import fr.unice.polytech.pel.polyescape.Data.EscapeGame;
import fr.unice.polytech.pel.polyescape.Data.Joueur;
import fr.unice.polytech.pel.polyescape.Data.Partie;
import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.Transmission.InvalidJsonRequest;
import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import org.json.JSONObject;

import javax.websocket.Session;
import java.util.Optional;

/**
 * @author Gaetan Vialon
 * Created the 15/01/2018.
 */
public class CreatePartieRequest implements Request {
    private Gestionnaire gestionnaire = Gestionnaire.getInstance();
    private int id;

    public CreatePartieRequest(String message, Session session) {
        id = decodeCreatePartie(message,session);
    }


    private int decodeCreatePartie(String message,Session session){
        JSONObject decode = new JSONObject(message);
        EscapeGame escapeGame = gestionnaire.getEscapeGame(decode.getString(JsonArguments.ESCAPEGAME.name()));
        if (escapeGame != null)
            return gestionnaire.createNewPartie(new Partie(escapeGame,
                    new Joueur(decode.getString(JsonArguments.USERNAME.name()),session)));
        return 0;
    }


    @Override
    public JSONObject getAnswer() {
        if (id==0)
            return new JSONObject()
                    .put(JsonArguments.REPONSE.name(),JsonArguments.KO.name());
        return new JSONObject()
                .put(JsonArguments.REPONSE.name(),JsonArguments.OK.name())
                .put(JsonArguments.ID.name(),id);
    }
}
