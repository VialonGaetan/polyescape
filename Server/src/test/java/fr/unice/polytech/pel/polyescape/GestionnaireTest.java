package fr.unice.polytech.pel.polyescape;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        //assertEquals(0,gestionnaire.getParties().size());
    }

    @Test
    public void getGameByIDNoExist(){
        assertEquals(gestionnaire.getPartieByID(0),null);

    }

}