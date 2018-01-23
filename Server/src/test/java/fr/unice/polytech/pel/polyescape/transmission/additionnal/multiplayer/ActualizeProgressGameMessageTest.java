package fr.unice.polytech.pel.polyescape.transmission.additionnal.multiplayer;

import fr.unice.polytech.pel.polyescape.data.Enigme;
import fr.unice.polytech.pel.polyescape.data.Joueur;
import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Gaetan Vialon
 * Created the 22/01/2018.
 */
public class ActualizeProgressGameMessageTest {

    private Map<Joueur,List<Enigme>> association;
    private List<Enigme> enigmes;
    private String username;
    private Joueur joueur;

    @Before
    public void setUP(){
        enigmes = new ArrayList<>();
        enigmes.add(new Enigme("1","1","1"));
        enigmes.add(new Enigme("2","2","2"));
        enigmes.add(new Enigme("3","3","3"));
        association = new HashMap<>();
        username = "pierre";
        joueur = new Joueur(username,null);
        association.put(joueur,enigmes);
    }

    @Test
    public void firstQuestion(){
        JSONObject reponse = new ActualizeProgressGameMessage(association).messageInJson().getJSONArray(JsonArguments.JOUEURS.toString()).getJSONObject(0);
        assertEquals(0,reponse.getInt(JsonArguments.ACTUAL.toString()));
        assertEquals(username,reponse.getString(JsonArguments.USERNAME.toString()));
        assertEquals(association.get(joueur).size(),reponse.getInt(JsonArguments.TOTAL.toString()));
    }

    @Test
    public void failQuestion(){
        JSONObject reponse = new ActualizeProgressGameMessage(association).messageInJson().getJSONArray(JsonArguments.JOUEURS.toString()).getJSONObject(0);
        assertEquals(0,reponse.getInt(JsonArguments.ACTUAL.toString()));
        assertEquals(username,reponse.getString(JsonArguments.USERNAME.toString()));
        assertEquals(association.get(joueur).size(),reponse.getInt(JsonArguments.TOTAL.toString()));
        association.get(joueur).get(0).checkAnswer("zuuuut");
        reponse = new ActualizeProgressGameMessage(association).messageInJson().getJSONArray(JsonArguments.JOUEURS.toString()).getJSONObject(0);
        assertEquals(0,reponse.getInt(JsonArguments.ACTUAL.toString()));
        assertEquals(username,reponse.getString(JsonArguments.USERNAME.toString()));
        assertEquals(association.get(joueur).size(),reponse.getInt(JsonArguments.TOTAL.toString()));

    }

    @Test
    public void validateQuestions(){
        association.get(joueur).get(0).checkAnswer("1");
        JSONObject reponse = new ActualizeProgressGameMessage(association).messageInJson().getJSONArray(JsonArguments.JOUEURS.toString()).getJSONObject(0);
        assertEquals(1,reponse.getInt(JsonArguments.ACTUAL.toString()));
        assertEquals(username,reponse.getString(JsonArguments.USERNAME.toString()));
        assertEquals(association.get(joueur).size(),reponse.getInt(JsonArguments.TOTAL.toString()));
        association.get(joueur).get(1).checkAnswer("2");
        reponse = new ActualizeProgressGameMessage(association).messageInJson().getJSONArray(JsonArguments.JOUEURS.toString()).getJSONObject(0);
        assertEquals(2,reponse.getInt(JsonArguments.ACTUAL.toString()));
        assertEquals(username,reponse.getString(JsonArguments.USERNAME.toString()));
        assertEquals(association.get(joueur).size(),reponse.getInt(JsonArguments.TOTAL.toString()));
        association.get(joueur).get(2).checkAnswer("3");
        reponse = new ActualizeProgressGameMessage(association).messageInJson().getJSONArray(JsonArguments.JOUEURS.toString()).getJSONObject(0);
        assertEquals(3,reponse.getInt(JsonArguments.ACTUAL.toString()));
        assertEquals(username,reponse.getString(JsonArguments.USERNAME.toString()));
        assertEquals(association.get(joueur).size(),reponse.getInt(JsonArguments.TOTAL.toString()));
    }

}