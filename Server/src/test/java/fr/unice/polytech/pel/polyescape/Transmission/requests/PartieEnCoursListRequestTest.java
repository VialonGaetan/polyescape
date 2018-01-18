package fr.unice.polytech.pel.polyescape.Transmission.requests;

import fr.unice.polytech.pel.polyescape.Data.Joueur;
import fr.unice.polytech.pel.polyescape.Data.Partie;
import fr.unice.polytech.pel.polyescape.Gestionnaire;

/**
 * @author Gaetan Vialon
 * Created the 16/01/2018.
 */
public class PartieEnCoursListRequestTest {

    public void setUp(){
        PartieEnCoursListRequest partieEnCoursListRequest = new PartieEnCoursListRequest();
        System.out.println(partieEnCoursListRequest.getAnswer());
        Gestionnaire.getInstance().createNewPartie(new Partie(Gestionnaire.getInstance().getEscapeGamesDisponible().get(0),new Joueur("lol",null)));
        System.out.println(Gestionnaire.getInstance().getParties().size());
        for (Partie partie: Gestionnaire.getInstance().getParties().values()) {
            System.out.println(partie.hasStart());
        }
        System.out.println(partieEnCoursListRequest.getAnswer());
    }

}