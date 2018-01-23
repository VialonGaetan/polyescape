package fr.unice.polytech.pel.polyescape.transmission.requests;

import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.data.GameMaster;
import fr.unice.polytech.pel.polyescape.data.Partie;
import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.websocket.Session;
import java.util.logging.Logger;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public class PartieEnCoursListRequest implements Request {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private GameMaster gameMaster;

    public PartieEnCoursListRequest(Session session) {
        gameMaster = new GameMaster(session);
        Gestionnaire.getInstance().setSessionMG(session);
        logger.info("Recupération de la liste des parties en cours");
    }


    @Override
    public JSONObject answerInJson() {
        JSONArray jsonArray = new JSONArray();
        for (Partie partie : Gestionnaire.getInstance().getParties().values()) {
            if (partie.hasStart())
                jsonArray.put(partie.toJson());
        }
        return new JSONObject().put(JsonArguments.REPONSE.toString(),JsonArguments.INFOS.toString())
                .put(JsonArguments.PARTIEENCOURS.toString(),jsonArray);
    }
}
