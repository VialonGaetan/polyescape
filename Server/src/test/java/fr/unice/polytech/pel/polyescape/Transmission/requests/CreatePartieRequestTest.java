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

/**
 * @author Gaetan Vialon
 * Created the 15/01/2018.
 */
public class CreatePartieRequestTest {

    private Gestionnaire gestionnaire = Gestionnaire.getInstance();
    private CreatePartieRequest createPartieRequest;
    private String request;
    private Session session;
    private String name;

    @Before
    public void setUp()
    {
        name = "Claude";
        request = new JSONObject().put(JsonArguments.REQUEST.toString(), TypeRequest.CREATE_PARTIE)
                .put(JsonArguments.USERNAME.toString(),name)
                .put(JsonArguments.ESCAPEGAME.toString(),gestionnaire.getEscapeGamesDisponible().get(0).getName())
                .put(JsonArguments.TEAMNAME.toString(),"")
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
    public void createNewGame(){
        System.out.println(request);
        int lol = gestionnaire.getPartieEnCours().values().size();
        createPartieRequest = new CreatePartieRequest(request,session);
        assertEquals(lol +1 ,gestionnaire.getPartieEnCours().values().size());
        System.out.println(createPartieRequest.getAnswer());
    }
}