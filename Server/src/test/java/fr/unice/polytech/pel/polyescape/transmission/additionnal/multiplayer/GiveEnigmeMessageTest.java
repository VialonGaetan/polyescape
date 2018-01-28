package fr.unice.polytech.pel.polyescape.transmission.additionnal.multiplayer;

import fr.unice.polytech.pel.polyescape.data.Enigme;
import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Gaetan Vialon
 * Created the 22/01/2018.
 */
public class GiveEnigmeMessageTest {

    private Enigme engine;
    private JSONObject message;
    private String name;
    private String description;
    private String reponse;
    private int time;

    @Before
    public void setUp() throws Exception {
        name = "Enigme1";
        description = "Description1";
        reponse="1";
        time = 1;
        engine = new Enigme(name,description,reponse);
    }

    @Test
    public void goodMessage() {
        System.out.println();
        message = new GiveEnigmeMessage(engine,time).messageInJson();
        assertEquals(description,message.getString(JsonArguments.INFOS.toString()));
        assertEquals(name,message.getString(JsonArguments.NOM.toString()));
        assertEquals(time,message.getInt(JsonArguments.TEMPS.toString()));

    }
}