package fr.unice.polytech.pel.polyescape.transmission.requests;

import fr.unice.polytech.pel.polyescape.Gestionnaire;
import fr.unice.polytech.pel.polyescape.data.Joueur;
import fr.unice.polytech.pel.polyescape.data.Partie;
import fr.unice.polytech.pel.polyescape.data.PartieEnEquipe;
import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Gaetan Vialon
 * Created the 18/01/2018.
 */
public class SalonListRequestTest {

    private Gestionnaire gestionnaire;
    private SalonListRequest request;

    @Test
    public void exemple() {
        gestionnaire = Gestionnaire.getInstance();
        request = new SalonListRequest();
        int partieEnAttente = 0;
        for (Partie partie: gestionnaire.getParties().values()) {
            if (!partie.hasStart())
                partieEnAttente++;
        }
        assertEquals(partieEnAttente,request.answerInJson().getJSONArray(JsonArguments.PARTIEATTENTE.toString()).length());

    }

}