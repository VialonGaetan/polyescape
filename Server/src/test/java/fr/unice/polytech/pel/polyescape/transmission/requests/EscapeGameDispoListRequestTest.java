package fr.unice.polytech.pel.polyescape.transmission.requests;

import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Gaetan Vialon
 * Created the 28/01/2018.
 */
public class EscapeGameDispoListRequestTest {

    private Gestionnaire gestionnaire;
    private EscapeGameDispoListRequest request;

    @Before
    public void setUp() throws Exception {
        gestionnaire = Gestionnaire.getInstance();
        request = new EscapeGameDispoListRequest();
    }

    @Test
    public void checkNumberOfEscapGame() {
        assertEquals(gestionnaire.getEscapeGamesDisponible().size(),request.answerInJson().getJSONArray(JsonArguments.ESCAPEGAMES.toString()).length());
    }
}