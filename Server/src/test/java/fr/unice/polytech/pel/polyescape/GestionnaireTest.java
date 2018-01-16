package fr.unice.polytech.pel.polyescape;

import fr.unice.polytech.pel.polyescape.Data.TypePartie;
import fr.unice.polytech.pel.polyescape.Transmission.InvalidJsonRequest;
import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import fr.unice.polytech.pel.polyescape.Transmission.TypeRequest;
import fr.unice.polytech.pel.polyescape.Transmission.requests.CreatePartieRequest;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public class GestionnaireTest {

    private Gestionnaire gestionnaire;

    @Before
    public void setUp()
    {
        gestionnaire = Gestionnaire.getInstance();

    }


    @Test
    public void initialisation()
    {
        assertEquals(0,gestionnaire.getPartieEnCours().size());
    }

    @Test
    public void getGameByIDNoExist(){
        assertEquals(gestionnaire.getPartieByID(0),null);

    }

}