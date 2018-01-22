package fr.unice.polytech.pel.polyescape.transmission.additionnal.multiplayer;

import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Gaetan Vialon
 * Created the 22/01/2018.
 */
public class EndGameMessageTest {

    private int score;
    private String message;
    private JSONObject messageInJson;

    @Before
    public void setUP(){
        score = 50;
    }

    @Test
    public void sendMessage() {
        messageInJson = new EndGameMessage(score).messageInJson();
        message = new EndGameMessage(score).createMessageToSend();
        assertTrue(message.contains(JsonArguments.FINISH.toString()));
        assertEquals(score,messageInJson.getInt(JsonArguments.SCORE.toString()));
    }
}