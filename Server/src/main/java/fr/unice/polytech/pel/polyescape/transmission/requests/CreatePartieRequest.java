package fr.unice.polytech.pel.polyescape.transmission.requests;

import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.data.EscapeGame;
import fr.unice.polytech.pel.polyescape.data.Joueur;
import fr.unice.polytech.pel.polyescape.data.Partie;
import fr.unice.polytech.pel.polyescape.data.PartieEnEquipe;
import fr.unice.polytech.pel.polyescape.transmission.InvalidJsonRequest;
import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import org.json.JSONObject;

import javax.websocket.Session;
import java.util.logging.Logger;

/**
 * @author Gaetan Vialon
 * Created the 15/01/2018.
 */
public class CreatePartieRequest implements Request {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private Gestionnaire gestionnaire = Gestionnaire.getInstance();
    private int id;
    private Joueur joueur;

    public CreatePartieRequest(String message, Session session) {
        logger.info("Creation d'une nouvelle partie");
        id = decodeCreatePartie(message, session);
    }


    private int decodeCreatePartie(String message, Session session){
        try {
            JSONObject decode = new JSONObject(message);
            EscapeGame escapeGame = gestionnaire.getEscapeGame(decode.getString(JsonArguments.ESCAPEGAME.toString()));
            String teamName = decode.getString(JsonArguments.TEAMNAME.toString());
            joueur = new Joueur(decode.getString(JsonArguments.USERNAME.toString()), session);
            if (escapeGame != null){
                if(teamName.isEmpty())
                    return gestionnaire.createNewPartie(new Partie(escapeGame,joueur));

                else
                    return gestionnaire.createNewPartie(new PartieEnEquipe(escapeGame,joueur,teamName));
            }
            return 0;
        } catch (Exception e) {
            throw new InvalidJsonRequest();
        }
    }

    @Override
    public JSONObject answerInJson() {
        if (id == 0)
            return new JSONObject()
                    .put(JsonArguments.REPONSE.toString(), JsonArguments.KO.toString());
        else if (gestionnaire.getPartieByID(id).hasStart())
            return new JSONObject()
                    .put(JsonArguments.REPONSE.toString(), JsonArguments.OK.toString())
                    .put(JsonArguments.IDPARTIE.toString(), id)
                    .put(JsonArguments.NOM.toString(),gestionnaire.getPartieByID(id).getCurrentEnigmesOfaPlayer(joueur).get().getName())
                    .put(JsonArguments.INFOS.toString(),gestionnaire.getPartieByID(id).getCurrentEnigmesOfaPlayer(joueur).get().getDescription())
                    .put(JsonArguments.TEMPS.toString(),gestionnaire.getPartieByID(id).getTime());
        else
            return new JSONObject()
                    .put(JsonArguments.REPONSE.toString(), JsonArguments.OK.toString())
                    .put(JsonArguments.IDPARTIE.toString(), id);
    }
}
