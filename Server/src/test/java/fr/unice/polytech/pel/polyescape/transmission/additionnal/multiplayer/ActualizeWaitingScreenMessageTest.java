package fr.unice.polytech.pel.polyescape.transmission.additionnal.multiplayer;

import fr.unice.polytech.pel.polyescape.data.Joueur;
import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Gaetan Vialon
 * Created the 22/01/2018.
 */
public class ActualizeWaitingScreenMessageTest {
    private Joueur joueur;
    private Joueur joueur2;
    private String name2;
    private String name;
    private Map<Joueur,Boolean> ready;
    private JSONObject message;

    @Before
    public void setUp() throws Exception {
        name = "Claude";
        name2 = "Polo";
        joueur = new Joueur(name,null);
        joueur2 = new Joueur(name2,null);
        ready = new HashMap<>();
    }

    @Test
    public void onePlayer() {
        ready.put(joueur,false);
        message = new ActualizeWaitingScreenMessage(ready).messageInJson();
        assertEquals(JsonArguments.ACTUALISE.toString(),message.getString(JsonArguments.REPONSE.toString()));
        assertEquals(1,message.getJSONArray(JsonArguments.JOUEURS.toString()).length());
        assertEquals(name,message.getJSONArray(JsonArguments.JOUEURS.toString()).getJSONObject(0).getString(JsonArguments.USERNAME.toString()));
        assertEquals(false,message.getJSONArray(JsonArguments.JOUEURS.toString()).getJSONObject(0).getBoolean(JsonArguments.READY.toString()));
    }

    @Test
    public void joinGameAndSetHimReady() {
        ready.put(joueur,false);
        ready.put(joueur2,true);
        message = new ActualizeWaitingScreenMessage(ready).messageInJson();
        assertEquals(JsonArguments.ACTUALISE.toString(),message.getString(JsonArguments.REPONSE.toString()));
        assertEquals(2,message.getJSONArray(JsonArguments.JOUEURS.toString()).length());
        assertEquals(name2,message.getJSONArray(JsonArguments.JOUEURS.toString()).getJSONObject(0).getString(JsonArguments.USERNAME.toString()));
        assertEquals(true,message.getJSONArray(JsonArguments.JOUEURS.toString()).getJSONObject(0).getBoolean(JsonArguments.READY.toString()));
        assertEquals(name,message.getJSONArray(JsonArguments.JOUEURS.toString()).getJSONObject(1).getString(JsonArguments.USERNAME.toString()));
        assertEquals(false,message.getJSONArray(JsonArguments.JOUEURS.toString()).getJSONObject(1).getBoolean(JsonArguments.READY.toString()));
    }


}