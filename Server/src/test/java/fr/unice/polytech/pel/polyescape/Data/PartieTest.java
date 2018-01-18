package fr.unice.polytech.pel.polyescape.Data;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Gaetan Vialon
 * Created the 16/01/2018.
 */
public class PartieTest {

    private Joueur joueur;
    private List<Enigme> enigmes;
    private EscapeGame escapeGame;
    private Partie partie;

    @Before
    public void setUpPartyOnePlayer()
    {
        enigmes = new ArrayList<>();
        enigmes.add(new Enigme("1","1","1"));
        enigmes.add(new Enigme("2","2","2"));
        enigmes.add(new Enigme("3","3","3"));

       joueur = new Joueur("Loic",null);

       escapeGame = new EscapeGame("titi",enigmes,50);
       partie = new Partie(escapeGame,joueur);

    }


    @Test
    public void initialisationGame()
    {
        assertTrue(partie.hasStart());
        assertEquals(escapeGame.getEnigmes().size(),partie.getEnigmesOfaPlayer(joueur).size());
        assertEquals(escapeGame.getEnigmes(),partie.getEnigmesOfaPlayer(joueur));
        assertEquals(escapeGame.getEnigmes().get(0),partie.getEnigmesOfaPlayer(joueur).get(0));
        assertEquals(escapeGame.getEnigmes().get(1),partie.getEnigmesOfaPlayer(joueur).get(1));
        assertEquals(escapeGame.getEnigmes().get(2),partie.getEnigmesOfaPlayer(joueur).get(2));
        assertEquals(escapeGame.getEnigmes().get(0),partie.getCurrentEnigmesOfaPlayer(joueur).get());
    }


    @Test
    public void validateFirstResponseAndDontValidateTheNext()
    {
        partie.getCurrentEnigmesOfaPlayer(joueur).get().checkAnswer(partie.getCurrentEnigmesOfaPlayer(joueur).get().getReponse());
        assertEquals(escapeGame.getEnigmes().size(),partie.getEnigmesOfaPlayer(joueur).size());
        assertEquals(escapeGame.getEnigmes().get(1),partie.getCurrentEnigmesOfaPlayer(joueur).get());
        partie.getCurrentEnigmesOfaPlayer(joueur).get().checkAnswer("je suis null");
        assertEquals(escapeGame.getEnigmes().get(1),partie.getCurrentEnigmesOfaPlayer(joueur).get());
    }
}