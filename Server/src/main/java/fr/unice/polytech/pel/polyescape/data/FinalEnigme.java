package fr.unice.polytech.pel.polyescape.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Gaetan Vialon
 * Created the 24/01/2018.
 */
public class FinalEnigme extends Enigme {

    public FinalEnigme(String name, String description, String reponse) {
        super(name, description, reponse);
    }

    public List<String> getIndiceForXPlayers(int sizeTeam) {
        List<String> indices = new ArrayList<>();
        int length = reponse.length();
        double temp = Math.ceil((double)(length)/(double)(sizeTeam));

        for (int i = 0; i < length; i += temp) {
            indices.add(reponse.substring(i, Math.min(length,  i + (int) temp)));
        }
        while (indices.size() < sizeTeam)
            indices.add("");

        return indices;
    }
}
