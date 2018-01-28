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
 * Created the 22/01/2018.
 */
public class JoinTeamRequestTest {

    private int idpartie;
    private Partie partie;
    private Gestionnaire gestionnaire;
    private EscapeGame escapeGame;
    private List<Enigme> enigmes;
    private Joueur joueur;
    private Joueur joueur2;
    private Joueur joueur3;
    private Joueur joueur4;
    private Joueur joueur5;
    private JoinTeamRequest request;
    private JSONObject requestMessage;

    @Before
    public void setUp() throws Exception {
        gestionnaire = Gestionnaire.getInstance();
        enigmes = new ArrayList<>();
        joueur = new Joueur("Pierre",null);
        joueur2 = new Joueur("Paul",null);
        joueur3 = new Joueur("Jacques",null);
        joueur4 = new Joueur("Dupont",null);
        joueur5 = new Joueur("Jean-pierre",null);
        enigmes.add(new Enigme("1","1","1"));
        enigmes.add(new Enigme("2","2","2"));
        enigmes.add(new Enigme("3","3","3"));
        escapeGame = new EscapeGame("escape","escape",enigmes,60);
        partie = new PartieEnEquipe(escapeGame,joueur,"teamL");
        idpartie = gestionnaire.createNewPartie(partie);
    }


    @Test
    public void joinEquipe() {
        requestMessage = new JSONObject().put(JsonArguments.REQUEST.toString(), TypeRequest.JOIN_TEAM)
                .put(JsonArguments.IDPARTIE.toString(),idpartie)
                .put(JsonArguments.USERNAME.toString(),joueur2.getNom());
        assertEquals(1,gestionnaire.getPartieByID(idpartie).getJoueurs().size());
        request = new JoinTeamRequest(requestMessage.toString(),null);
        assertEquals(2,gestionnaire.getPartieByID(idpartie).getJoueurs().size());
        assertTrue(gestionnaire.getPartieByID(idpartie).getJoueurs().contains(joueur));
        assertTrue(gestionnaire.getPartieByID(idpartie).getJoueurs().contains(joueur2));
        assertFalse(gestionnaire.getPartieByID(idpartie).getJoueurs().contains(joueur3));
        assertFalse(gestionnaire.getPartieByID(idpartie).getJoueurs().contains(joueur4));
        assertEquals(JsonArguments.OK.toString(),request.answerInJson().getString(JsonArguments.REPONSE.toString()));
        requestMessage = new JSONObject().put(JsonArguments.REQUEST.toString(), TypeRequest.JOIN_TEAM)
                .put(JsonArguments.IDPARTIE.toString(),idpartie)
                .put(JsonArguments.USERNAME.toString(),joueur3.getNom());
        assertEquals(2,gestionnaire.getPartieByID(idpartie).getJoueurs().size());
        request = new JoinTeamRequest(requestMessage.toString(),null);
        assertEquals(3,gestionnaire.getPartieByID(idpartie).getJoueurs().size());
        assertTrue(gestionnaire.getPartieByID(idpartie).getJoueurs().contains(joueur));
        assertTrue(gestionnaire.getPartieByID(idpartie).getJoueurs().contains(joueur2));
        assertTrue(gestionnaire.getPartieByID(idpartie).getJoueurs().contains(joueur3));
        assertFalse(gestionnaire.getPartieByID(idpartie).getJoueurs().contains(joueur4));
        assertEquals(JsonArguments.OK.toString(),request.answerInJson().getString(JsonArguments.REPONSE.toString()));
        requestMessage = new JSONObject().put(JsonArguments.REQUEST.toString(), TypeRequest.JOIN_TEAM)
                .put(JsonArguments.IDPARTIE.toString(),idpartie)
                .put(JsonArguments.USERNAME.toString(),joueur4.getNom());
        assertEquals(3,gestionnaire.getPartieByID(idpartie).getJoueurs().size());
        request = new JoinTeamRequest(requestMessage.toString(),null);
        assertEquals(4,gestionnaire.getPartieByID(idpartie).getJoueurs().size());
        assertTrue(gestionnaire.getPartieByID(idpartie).getJoueurs().contains(joueur));
        assertTrue(gestionnaire.getPartieByID(idpartie).getJoueurs().contains(joueur2));
        assertTrue(gestionnaire.getPartieByID(idpartie).getJoueurs().contains(joueur3));
        assertTrue(gestionnaire.getPartieByID(idpartie).getJoueurs().contains(joueur4));
        assertEquals(JsonArguments.OK.toString(),request.answerInJson().getString(JsonArguments.REPONSE.toString()));
    }


    @Test
    public void teamFull() {

        requestMessage = new JSONObject().put(JsonArguments.REQUEST.toString(), TypeRequest.JOIN_TEAM)
                .put(JsonArguments.IDPARTIE.toString(),idpartie)
                .put(JsonArguments.USERNAME.toString(),joueur2.getNom());
        new JoinTeamRequest(requestMessage.toString(),null);
        requestMessage = new JSONObject().put(JsonArguments.REQUEST.toString(), TypeRequest.JOIN_TEAM)
                .put(JsonArguments.IDPARTIE.toString(),idpartie)
                .put(JsonArguments.USERNAME.toString(),joueur3.getNom());
        new JoinTeamRequest(requestMessage.toString(),null);
        requestMessage = new JSONObject().put(JsonArguments.REQUEST.toString(), TypeRequest.JOIN_TEAM)
                .put(JsonArguments.IDPARTIE.toString(),idpartie)
                .put(JsonArguments.USERNAME.toString(),joueur4.getNom());
        new JoinTeamRequest(requestMessage.toString(),null);
        assertEquals(4,gestionnaire.getPartieByID(idpartie).getJoueurs().size());
        requestMessage = new JSONObject().put(JsonArguments.REQUEST.toString(), TypeRequest.JOIN_TEAM)
                .put(JsonArguments.IDPARTIE.toString(),idpartie)
                .put(JsonArguments.USERNAME.toString(),joueur5.getNom());
        request = new JoinTeamRequest(requestMessage.toString(),null);
        assertEquals(4,gestionnaire.getPartieByID(idpartie).getJoueurs().size());
        assertFalse(gestionnaire.getPartieByID(idpartie).getJoueurs().contains(joueur5));
        assertEquals(JsonArguments.KO.toString(),request.answerInJson().getString(JsonArguments.REPONSE.toString()));

    }
}