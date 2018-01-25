package fr.unice.polytech.pel.polyescape.data;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Gaetan Vialon
 * Created the 24/01/2018.
 */
public class FinalEnigmeTest {

    private FinalEnigme finalEnigme;

    @Before
    public void setUp() throws Exception {
        finalEnigme = new FinalEnigme("lol","lol","lol");
    }

    @Test
    public void decoupage() {
        assertEquals(3, finalEnigme.getIndiceForXPlayers(3).size());
    }
}