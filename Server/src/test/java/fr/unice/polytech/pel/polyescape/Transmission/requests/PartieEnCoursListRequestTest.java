package fr.unice.polytech.pel.polyescape.Transmission.requests;

import fr.unice.polytech.pel.polyescape.Data.Joueur;
import fr.unice.polytech.pel.polyescape.Data.GameMaster;
import fr.unice.polytech.pel.polyescape.Data.Partie;
import fr.unice.polytech.pel.polyescape.Data.TypePartie;
import fr.unice.polytech.pel.polyescape.Gestionnaire;
import org.junit.Test;

/**
 * @author Gaetan Vialon
 * Created the 16/01/2018.
 */
public class PartieEnCoursListRequestTest {

    @Test
    public void setUp(){
        PartieEnCoursListRequest partieEnCoursListRequest = new PartieEnCoursListRequest(null);

        Gestionnaire.getInstance().createNewPartie(new Partie(Gestionnaire.getInstance().getEscapeGamesDisponible().get(0),new Joueur("lol",null), new GameMaster(null), TypePartie.SOLO));


    }

}