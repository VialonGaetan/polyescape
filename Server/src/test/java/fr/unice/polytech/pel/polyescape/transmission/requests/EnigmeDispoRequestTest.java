package fr.unice.polytech.pel.polyescape.transmission.requests;

import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Gaetan Vialon
 * Created the 16/01/2018.
 */
public class EnigmeDispoRequestTest {
    private Gestionnaire gestionnaire;
    private EnigmeDispoRequest request;

    @Before
    public void setUp() throws Exception {
        gestionnaire = Gestionnaire.getInstance();
        request = new EnigmeDispoRequest();
    }

    @Test
    public void checkNumberOfEnigmes() {
        assertEquals(gestionnaire.getEnigmeDisponible().size(),request.answerInJson().getJSONArray(JsonArguments.ENIGMES.toString()).length());
    }
}