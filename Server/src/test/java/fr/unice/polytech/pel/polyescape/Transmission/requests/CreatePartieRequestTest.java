package fr.unice.polytech.pel.polyescape.Transmission.requests;

import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.Transmission.InvalidJsonRequest;
import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import fr.unice.polytech.pel.polyescape.Transmission.TypeRequest;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import javax.websocket.Session;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Gaetan Vialon
 * Created the 15/01/2018.
 */
public class CreatePartieRequestTest {

    private Gestionnaire gestionnaire = Gestionnaire.getInstance();
    private CreatePartieRequest createPartieRequest;
    private String requestSolo;
    private String requestTeam;
    private Session session;
    private String name;

    @Before
    public void setUp()
    {
        name = "Claude";
        requestSolo = new JSONObject().put(JsonArguments.REQUEST.toString(), TypeRequest.CREATE_PARTIE)
                .put(JsonArguments.USERNAME.toString(),name)
                .put(JsonArguments.ESCAPEGAME.toString(),gestionnaire.getEscapeGamesDisponible().get(0).getName())
                .put(JsonArguments.TEAMNAME.toString(),"")
                .toString();

        requestTeam = new JSONObject().put(JsonArguments.REQUEST.toString(), TypeRequest.CREATE_PARTIE)
                .put(JsonArguments.USERNAME.toString(),name)
                .put(JsonArguments.ESCAPEGAME.toString(),gestionnaire.getEscapeGamesDisponible().get(0).getName())
                .put(JsonArguments.TYPE.toString(), TypePartie.TEAM)
                .toString();
        session = null;

    }


    @Test(expected = InvalidJsonRequest.class)
    public void invalidRequestTest()
    {
        createPartieRequest = new CreatePartieRequest(null,null);
        createPartieRequest.getAnswer();
    }

    @Test
    public void createNewGameSolo(){
        int numberPartieBeforeNewPartie = gestionnaire.getParties().values().size();
        createPartieRequest = new CreatePartieRequest(requestSolo,session);
        assertEquals(numberPartieBeforeNewPartie + 1,gestionnaire.getParties().values().size());
        checkReponse();
        checkSupplement();

    }

    @Test
    public void createNewGameWithTeam(){
        int numberPartieBeforeNewPartie = gestionnaire.getParties().values().size();
        createPartieRequest = new CreatePartieRequest(requestTeam,session);
        assertEquals(numberPartieBeforeNewPartie + 1,gestionnaire.getParties().values().size());
        checkReponse();
    }

    private void checkReponse(){
        assertTrue(createPartieRequest.getAnswer().contains(JsonArguments.IDPARTIE.toString()));
        assertTrue(createPartieRequest.getAnswer().contains(JsonArguments.REPONSE.toString()));
    }

    private void checkSupplement(){
        assertTrue(createPartieRequest.getAnswer().contains(JsonArguments.INFOS.toString()));
        assertTrue(createPartieRequest.getAnswer().contains(JsonArguments.TEMPS.toString()));
        assertTrue(createPartieRequest.getAnswer().contains(JsonArguments.NOM.toString()));
    }
}