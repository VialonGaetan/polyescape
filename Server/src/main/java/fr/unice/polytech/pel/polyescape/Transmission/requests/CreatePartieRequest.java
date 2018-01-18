package fr.unice.polytech.pel.polyescape.Transmission.requests;

import fr.unice.polytech.pel.polyescape.Data.EscapeGame;
import fr.unice.polytech.pel.polyescape.Data.Joueur;
import fr.unice.polytech.pel.polyescape.Data.Partie;
import fr.unice.polytech.pel.polyescape.Data.TypePartie;
import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.Transmission.InvalidJsonRequest;
import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
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
    private TypePartie typePartie;
    private Joueur joueur;

    public CreatePartieRequest(String message, Session session) {
        logger.info("Creation d'une nouvelle partie");
        System.out.println(message);
        id = decodeCreatePartie(message, session);
    }


    private int decodeCreatePartie(String message, Session session) {
        try {
            JSONObject decode = new JSONObject(message);
            EscapeGame escapeGame = gestionnaire.getEscapeGame(decode.getString(JsonArguments.ESCAPEGAME.toString()));
            typePartie = TypePartie.valueOf(decode.getString(JsonArguments.TYPE.toString()));
            joueur = new Joueur(decode.getString(JsonArguments.USERNAME.toString()), session);
            if (escapeGame != null)
                return gestionnaire.createNewPartie(new Partie(escapeGame,
                        joueur,
                        typePartie));
            return 0;
        } catch (Exception e) {
            throw new InvalidJsonRequest();
        }
    }


    @Override
    public String getAnswer() {
        return answerInJson().toString();
    }

    @Override
    public JSONObject answerInJson() {
        if (id == 0)
            return new JSONObject()
                    .put(JsonArguments.REPONSE.toString(), JsonArguments.KO.toString());
        else if (typePartie.equals(TypePartie.SOLO) && gestionnaire.getPartieByID(id).getCurrentEnigmesOfaPlayer(joueur).isPresent())
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
