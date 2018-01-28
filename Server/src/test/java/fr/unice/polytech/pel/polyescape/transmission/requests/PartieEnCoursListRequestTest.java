package fr.unice.polytech.pel.polyescape.transmission.requests;

import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.data.Joueur;
import fr.unice.polytech.pel.polyescape.data.Partie;
import fr.unice.polytech.pel.polyescape.data.PartieEnEquipe;
import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;

import static org.junit.Assert.assertEquals;

/**
 * @author Gaetan Vialon
 * Created the 16/01/2018.
 */
public class PartieEnCoursListRequestTest {
    private Gestionnaire gestionnaire;
    private PartieEnCoursListRequest request;

    public void setUp(){
        gestionnaire = Gestionnaire.getInstance();
        request = new PartieEnCoursListRequest(null);
        int partieEnCours = 0;
        for (Partie partie: gestionnaire.getParties().values()) {
            if (partie.hasStart())
                partieEnCours++;
        }
        assertEquals(partieEnCours,request.answerInJson().getJSONArray(JsonArguments.PARTIEENCOURS.toString()).length());
    }

}