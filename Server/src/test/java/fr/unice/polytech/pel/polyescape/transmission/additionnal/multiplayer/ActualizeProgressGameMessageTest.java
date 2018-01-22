package fr.unice.polytech.pel.polyescape.transmission.additionnal.multiplayer;

import fr.unice.polytech.pel.polyescape.data.Enigme;
import fr.unice.polytech.pel.polyescape.data.Joueur;
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
    private ActualizeProgressGameMessage message;

    @Before
    public void setUP(){
        enigmes = new ArrayList<>();
        enigmes.add(new Enigme("1","1","1"));
        enigmes.add(new Enigme("2","2","2"));
        enigmes.add(new Enigme("3","3","3"));
        association = new HashMap<>();
        association.put(new Joueur("lol",null),enigmes);
    }

    @Test
    public void firstQuestion(){
        System.out.println(new ActualizeProgressGameMessage(association).createMessageToSend());
    }

}