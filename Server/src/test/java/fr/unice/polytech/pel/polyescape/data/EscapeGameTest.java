package fr.unice.polytech.pel.polyescape.data;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Gaetan Vialon
 * Created the 16/01/2018.
 */
public class EscapeGameTest {

    private List<Enigme> enigmes;
    private EscapeGame escapeGame;
    private final String nom = "Sherlock";
    private final int temps = 60;

    @Before
    public void setUp()
    {
        enigmes = new ArrayList<>();
        enigmes.add(new Enigme("1","1","1"));
        enigmes.add(new Enigme("2","2","2"));
        enigmes.add(new Enigme("3","3","3"));
        escapeGame = new EscapeGame(nom,"1",enigmes,temps);
    }


    @Test
    public void checkEscapeGame()
    {
        assertEquals(nom,escapeGame.getName());
        assertEquals(enigmes,escapeGame.getEnigmes());
        assertEquals(enigmes.size(),escapeGame.getEnigmes().size());
        assertEquals(temps,escapeGame.getTime());
    }

}