package fr.unice.polytech.pel.polyescape.transmission.requests;

import fr.unice.polytech.pel.polyescape.data.Enigme;
import fr.unice.polytech.pel.polyescape.data.Joueur;
import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import fr.unice.polytech.pel.polyescape.transmission.TypeRequest;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Gaetan Vialon
 * Created the 28/01/2018.
 */
public class HelpRequestTest {

    private JSONObject requestMessage;
    private Joueur joueur;
    private Enigme enigme;
    private HelpRequest request;

    @Before
    public void setUp() throws Exception {
        enigme = new Enigme("1","1","1");
        joueur = new Joueur("Claudus",null);
        requestMessage = new JSONObject().put(JsonArguments.IDPARTIE.toString(),1)
                                            .put(JsonArguments.USERNAME.toString(),joueur.getNom())
                                            .put(JsonArguments.ENIGME.toString(),enigme.getDescription())
                                            .put(JsonArguments.REQUEST.toString(), TypeRequest.HELP);
    }

    @Test
    public void noMJ() {
        request = new HelpRequest(requestMessage.toString(),null);
        assertEquals(JsonArguments.KO.toString(),request.answerInJson().getString(JsonArguments.REPONSE.toString()));

    }
}