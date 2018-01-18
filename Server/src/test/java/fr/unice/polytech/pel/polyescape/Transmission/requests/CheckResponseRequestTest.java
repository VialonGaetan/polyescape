package fr.unice.polytech.pel.polyescape.Transmission.requests;

import fr.unice.polytech.pel.polyescape.Data.*;
import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import fr.unice.polytech.pel.polyescape.Transmission.TypeRequest;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Gaetan Vialon
 * Created the 16/01/2018.
 */
public class CheckResponseRequestTest {

    private String request;
    private Gestionnaire gestionnaire = Gestionnaire.getInstance();
    private List<Enigme> enigmes;
    private Partie partie;
    private EscapeGame escapeGame;
    private Joueur joueur;
    private GameMaster maitreDuJeu;
    private int idPArtie;
    private CheckResponseRequest checkResponseRequest;
    private final String name = "Jean";
    private final Session session = null;

    @Before
    public void setUp()
    {
        enigmes = new ArrayList<>();
        enigmes.add(new Enigme("1","1","1"));
        enigmes.add(new Enigme("2","2","2"));
        enigmes.add(new Enigme("3","3","3"));
        escapeGame = new EscapeGame("1",enigmes,50);
        joueur = new Joueur(name,session);
        partie = new Partie(escapeGame, joueur, maitreDuJeu, TypePartie.SOLO);
        idPArtie = gestionnaire.createNewPartie(partie);

    }

    @Test
    public void testMauvaiseReponse(){
        request = new JSONObject().put(JsonArguments.REQUEST.toString(), TypeRequest.RESPONSE)
                .put(JsonArguments.USERNAME.toString(), name)
                .put(JsonArguments.IDPARTIE.toString(), idPArtie)
                .put(JsonArguments.REPONSE.toString(), "mauvaise Reponse").toString();
        checkResponseRequest = new CheckResponseRequest(request,session);
        assertEquals(getReponse(checkResponseRequest.answerInJson()),JsonArguments.KO.toString());
    }

    @Test
    public void testBonneReponse(){
        request = new JSONObject().put(JsonArguments.REQUEST.toString(), TypeRequest.RESPONSE)
                .put(JsonArguments.USERNAME.toString(), name)
                .put(JsonArguments.IDPARTIE.toString(), idPArtie)
                .put(JsonArguments.REPONSE.toString(), "1").toString();
        checkResponseRequest = new CheckResponseRequest(request,session);
        assertEquals(getReponse(checkResponseRequest.answerInJson()),JsonArguments.OK.toString());
    }

    @Test
    public void testFinDuJeu(){
        request = new JSONObject().put(JsonArguments.REQUEST.toString(), TypeRequest.RESPONSE)
                .put(JsonArguments.USERNAME.toString(), name)
                .put(JsonArguments.IDPARTIE.toString(), idPArtie)
                .put(JsonArguments.REPONSE.toString(), "1").toString();
        checkResponseRequest = new CheckResponseRequest(request,session);
        assertEquals(getReponse(checkResponseRequest.answerInJson()),JsonArguments.OK.toString());
        request = new JSONObject().put(JsonArguments.REQUEST.toString(), TypeRequest.RESPONSE)
                .put(JsonArguments.USERNAME.toString(), name)
                .put(JsonArguments.IDPARTIE.toString(), idPArtie)
                .put(JsonArguments.REPONSE.toString(), "2").toString();
        checkResponseRequest = new CheckResponseRequest(request,session);
        assertEquals(getReponse(checkResponseRequest.answerInJson()), JsonArguments.OK.toString());
        request = new JSONObject().put(JsonArguments.REQUEST.toString(), TypeRequest.RESPONSE)
                .put(JsonArguments.USERNAME.toString(), name)
                .put(JsonArguments.IDPARTIE.toString(), idPArtie)
                .put(JsonArguments.REPONSE.toString(), "3").toString();
        checkResponseRequest = new CheckResponseRequest(request,session);
        assertEquals(getReponse(checkResponseRequest.answerInJson()),JsonArguments.FINISH.toString());
    }

    public String getReponse(JSONObject answer){
        return answer.getString(JsonArguments.REPONSE.toString());
    }

}