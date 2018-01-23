package fr.unice.polytech.pel.polyescape.data;

import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Gaetan Vialon
 * Created the 16/01/2018.
 */
public class EnigmeTest {

    private Enigme enigme;
    private final String nom = "PremierEnigme";
    private final String description = "1+1=";
    private final String solution = "2";

    @Before
    public void setUp()
    {
        enigme = new Enigme(nom,description,solution);
    }


    @Test
    public void initialisationEnigme()
    {
        assertEquals(nom,enigme.getName());
        assertEquals(description,enigme.getDescription());
        assertEquals(solution,enigme.getReponse());
        assertFalse(enigme.isResolve());
    }


    @Test
    public void donnerUneMauvaiseReponse()
    {
        enigme.checkAnswer("4");
        assertFalse(enigme.isResolve());
    }

    @Test
    public void donnerUneBonneReponse()
    {
        enigme.checkAnswer("2");
        assertTrue(enigme.isResolve());
    }

    @Test
    public void checkJsonFormatAndInformation(){
        assertEquals(description,enigme.toJson().getString(JsonArguments.INFOS.toString()));
        assertEquals(nom,enigme.toJson().getString(JsonArguments.NOM.toString()));
        assertEquals(solution,enigme.toJson().getString(JsonArguments.REPONSE.toString()));
    }

}