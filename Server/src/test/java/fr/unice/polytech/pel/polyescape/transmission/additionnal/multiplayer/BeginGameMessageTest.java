package fr.unice.polytech.pel.polyescape.Transmission.additionnal.multiplayer;

import fr.unice.polytech.pel.polyescape.Data.Enigme;
import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Gaetan Vialon
 * Created the 22/01/2018.
 */
public class BeginGameMessageTest {

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
        message = new BeginGameMessage(engine,time).messageInJson();
        assertEquals(description,message.getString(JsonArguments.INFOS.toString()));
        assertEquals(name,message.getString(JsonArguments.NOM.toString()));
        assertEquals(time,message.getInt(JsonArguments.TEMPS.toString()));

    }
}