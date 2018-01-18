package fr.unice.polytech.pel.polyescape.Data;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

       escapeGame = new EscapeGame("titi",enigmes,50);
       partieSolo = new Partie(escapeGame,joueur,TypePartie.SOLO);
       partieTeam = new Partie(escapeGame,joueur,TypePartie.TEAM);

    }


    @Test
    public void initialisationGame()
    {
        assertTrue(partieSolo.hasStart());
        assertEquals(escapeGame.getEnigmes().size(), partieSolo.getEnigmesOfaPlayer(joueur).size());
        assertEquals(escapeGame.getEnigmes(), partieSolo.getEnigmesOfaPlayer(joueur));
        assertEquals(escapeGame.getEnigmes().get(0), partieSolo.getEnigmesOfaPlayer(joueur).get(0));
        assertEquals(escapeGame.getEnigmes().get(1), partieSolo.getEnigmesOfaPlayer(joueur).get(1));
        assertEquals(escapeGame.getEnigmes().get(2), partieSolo.getEnigmesOfaPlayer(joueur).get(2));
        assertEquals(escapeGame.getEnigmes().get(0), partieSolo.getCurrentEnigmesOfaPlayer(joueur).get());
    }


    @Test
    public void validateFirstResponseAndDontValidateTheNext()
    {
        partieSolo.getCurrentEnigmesOfaPlayer(joueur).get().checkAnswer(partieSolo.getCurrentEnigmesOfaPlayer(joueur).get().getReponse());
        assertEquals(escapeGame.getEnigmes().size(), partieSolo.getEnigmesOfaPlayer(joueur).size());
        assertEquals(escapeGame.getEnigmes().get(1), partieSolo.getCurrentEnigmesOfaPlayer(joueur).get());
        partieSolo.getCurrentEnigmesOfaPlayer(joueur).get().checkAnswer("je suis null");
        assertEquals(escapeGame.getEnigmes().get(1), partieSolo.getCurrentEnigmesOfaPlayer(joueur).get());
    }


    @Test
    public void initialisationTeam(){
        assertFalse(partieTeam.hasStart());
        partieTeam.joinPartie(secondJoueur);
        assertFalse(partieTeam.hasStart());
        partieTeam.setJoueurReadyOrNot(joueur,true);
        assertFalse(partieTeam.hasStart());
        partieTeam.setJoueurReadyOrNot(joueur,false);
        partieTeam.setJoueurReadyOrNot(secondJoueur,true);
        assertFalse(partieTeam.hasStart());
        partieTeam.setJoueurReadyOrNot(joueur,true);
        assertTrue(partieTeam.hasStart());
    }

}