package fr.unice.polytech.pel.polyescape.Transmission.requests;

import fr.unice.polytech.pel.polyescape.Data.Joueur;
import fr.unice.polytech.pel.polyescape.Data.PartieEnEquipe;
import fr.unice.polytech.pel.polyescape.Gestionnaire;

import static org.junit.Assert.*;

/**
 * @author Gaetan Vialon
 * Created the 18/01/2018.
 */
public class SalonListRequestTest {

    public void exemple() {

        Gestionnaire gestionnaire = Gestionnaire.getInstance();
        gestionnaire.createNewPartie(new PartieEnEquipe(gestionnaire.getEscapeGamesDisponible().get(0), new Joueur("lol", null), "PierreLeBoloss"));
        SalonListRequest request = new SalonListRequest();
        System.out.println(request.getAnswer());

    }

}