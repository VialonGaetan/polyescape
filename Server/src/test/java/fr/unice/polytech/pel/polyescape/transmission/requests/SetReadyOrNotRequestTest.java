package fr.unice.polytech.pel.polyescape.transmission.requests;

import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.data.*;
import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import fr.unice.polytech.pel.polyescape.transmission.TypeRequest;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Gaetan Vialon
 * Created the 28/01/2018.
 */
public class SetReadyOrNotRequestTest {


    private int idpartie;
    private Partie partie;
    private Gestionnaire gestionnaire;
    private EscapeGame escapeGame;
    private List<Enigme> enigmes;
    private Joueur joueur;
    private Joueur joueur2;
    private SetReadyOrNotRequest request;
    private JSONObject requestMessage;

    @Before
    public void setUp() {
        gestionnaire = Gestionnaire.getInstance();
        enigmes = new ArrayList<>();
        joueur = new Joueur("Jean-pierre",null);
        joueur2 = new Joueur("Papi",null);
        enigmes.add(new Enigme("1","1","1"));
        enigmes.add(new Enigme("2","2","2"));
        enigmes.add(new Enigme("3","3","3"));
        escapeGame = new EscapeGame("escape","escape",enigmes,60);
        partie = new PartieEnEquipe(escapeGame,joueur,"teamL");
        idpartie = gestionnaire.createNewPartie(partie);
    }

    @Test
    public void setSomeOneReady() {
        assertFalse(partie.hasStart());
        requestMessage = new JSONObject().put(JsonArguments.REQUEST.toString(), TypeRequest.SET_READY)
                                            .put(JsonArguments.USERNAME.toString(),joueur.getNom())
                                            .put(JsonArguments.IDPARTIE.toString(),idpartie);
        request = new SetReadyOrNotRequest(requestMessage.toString(),null);
        assertTrue(partie.hasStart());

    }

    @Test
    public void setSomeOneReadyThenUnReady() {
        assertFalse(partie.hasStart());
        partie.joinPartie(joueur2);
        requestMessage = new JSONObject().put(JsonArguments.REQUEST.toString(), TypeRequest.SET_READY)
                .put(JsonArguments.USERNAME.toString(),joueur.getNom())
                .put(JsonArguments.IDPARTIE.toString(),idpartie);
        request = new SetReadyOrNotRequest(requestMessage.toString(),null);
        assertFalse(partie.hasStart());
        requestMessage = new JSONObject().put(JsonArguments.REQUEST.toString(), TypeRequest.SET_READY)
                .put(JsonArguments.USERNAME.toString(),joueur2.getNom())
                .put(JsonArguments.IDPARTIE.toString(),idpartie);
        request = new SetReadyOrNotRequest(requestMessage.toString(),null);
        assertTrue(partie.hasStart());
    }
}