package fr.unice.polytech.pel.polyescape.data;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Gaetan Vialon
 * Created the 16/01/2018.
 */
public class PartieTest {

    private Joueur joueur;
    private Joueur secondJoueur;
    private List<Enigme> enigmes;
    private EscapeGame escapeGame;
    private Partie partieSolo;
    private Partie partieTeam;

    @Before
    public void setUpPartyOnePlayer()
    {
        enigmes = new ArrayList<>();
        enigmes.add(new Enigme("1","1","1"));
        enigmes.add(new Enigme("2","2","2"));
        enigmes.add(new Enigme("3","3","3"));

       joueur = new Joueur("Loic",null);
       secondJoueur = new Joueur("GuiGui",null);

       escapeGame = new EscapeGame("titi","toto",enigmes,50);
       partieSolo = new Partie(escapeGame,joueur);
       partieTeam = new PartieEnEquipe(escapeGame,joueur,"Grosse team");

    }


    @Test
    public void initialisationGame()
    {
        assertFalse(partieSolo.joinPartie(new Joueur("Robert",null)));
        assertTrue(partieSolo.hasStart());
        assertEquals(escapeGame.getEnigmes().size(), partieSolo.getEnigmesOfaPlayer(joueur).size());
        assertEquals(escapeGame.getEnigmes(), partieSolo.getEnigmesOfaPlayer(joueur));
        assertEquals(escapeGame.getEnigmes().get(0), partieSolo.getEnigmesOfaPlayer(joueur).get(0));
        assertEquals(escapeGame.getEnigmes().get(1), partieSolo.getEnigmesOfaPlayer(joueur).get(1));
        assertEquals(escapeGame.getEnigmes().get(2), partieSolo.getEnigmesOfaPlayer(joueur).get(2));
        assertEquals(escapeGame.getEnigmes().get(0), partieSolo.getCurrentEnigmesOfaPlayer(joueur).get());
        assertFalse(partieSolo.isFinish());
    }


    @Test
    public void validateFirstResponseAndDontValidateTheNext()
    {
        partieSolo.getCurrentEnigmesOfaPlayer(joueur).get().checkAnswer(partieSolo.getCurrentEnigmesOfaPlayer(joueur).get().getReponse());
        assertEquals(escapeGame.getEnigmes().size(), partieSolo.getEnigmesOfaPlayer(joueur).size());
        assertEquals(escapeGame.getEnigmes().get(1), partieSolo.getCurrentEnigmesOfaPlayer(joueur).get());
        partieSolo.getCurrentEnigmesOfaPlayer(joueur).get().checkAnswer("je suis null");
        assertEquals(escapeGame.getEnigmes().get(1), partieSolo.getCurrentEnigmesOfaPlayer(joueur).get());
        assertEquals(escapeGame.getEnigmes(),partieSolo.getEnigmesOfaPlayer(joueur));
        assertEquals(1,partieSolo.getJoueurs().size());
        assertEquals(1,partieSolo.numberOfPlayer());
    }


    @Test
    public void initialisationTeam(){
        assertFalse(partieTeam.hasStart());
        assertTrue(partieTeam.joinPartie(secondJoueur));
        assertFalse(partieTeam.hasStart());
        assertEquals(2,partieTeam.getJoueurs().size());
        assertEquals(2,partieTeam.numberOfPlayer());
        partieTeam.setJoueurReadyOrNot(joueur);
        assertFalse(partieTeam.hasStart());
        partieTeam.setJoueurReadyOrNot(joueur);
        partieTeam.setJoueurReadyOrNot(secondJoueur);
        assertFalse(partieTeam.hasStart());
        partieTeam.setJoueurReadyOrNot(joueur);
        assertTrue(partieTeam.hasStart());
        assertFalse(partieTeam.isFinish());
        assertTrue(escapeGame.getEnigmes().containsAll(partieTeam.getEnigmesOfaPlayer(joueur)));
        assertTrue(escapeGame.getEnigmes().containsAll(partieTeam.getEnigmesOfaPlayer(secondJoueur)));
        int nbEnigmes = 0;
        nbEnigmes = partieTeam.getEnigmesOfaPlayer(joueur).size() + partieTeam.getEnigmesOfaPlayer(secondJoueur).size();
        assertEquals(escapeGame.getEnigmes().size(),nbEnigmes);
    }

}