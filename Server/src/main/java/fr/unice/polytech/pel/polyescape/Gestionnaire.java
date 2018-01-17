package fr.unice.polytech.pel.polyescape;

import fr.unice.polytech.pel.polyescape.Data.Enigme;
import fr.unice.polytech.pel.polyescape.Data.EscapeGame;
import fr.unice.polytech.pel.polyescape.Data.Partie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public class Gestionnaire {

    private static Gestionnaire ourInstance = new Gestionnaire();
    private final Map<String,EscapeGame> escapeGamesDisponible;
    private final Map<String,Enigme> enigmeDisponible;
    private final Map<Integer,Partie> parties;
    private int id = 0;


    public static Gestionnaire getInstance() {
        return ourInstance;
    }

    private Gestionnaire() {
        escapeGamesDisponible = new HashMap<>();
        enigmeDisponible = new HashMap<>();
        parties = new HashMap<>();
        enigmeDisponible.put("Resoud l'operation",new Enigme("Resoud l'operation","Chaise * Tables =","42"));
        enigmeDisponible.put("Loi de Newton",new Enigme("Loi de Newton","En quelle année a été ecrit la deuxieme loi de Newton","1686"));
        enigmeDisponible.put("Le code pour les nuls",new Enigme("Le code pour les nuls","Dans quel rayon se trouve le livre \"Le code pour les nuls\"","Informatique"));
        enigmeDisponible.put("Upside down",new Enigme("Upside down","Quelle salle se trouve juste au dessus de la salle O+124","O+225"));
        enigmeDisponible.put("Le mot caché",new Enigme("Le mot caché","4 images forment un mot","escape"));
        escapeGamesDisponible.put("Que des numeros 10 dans ma team",new EscapeGame("Que des numeros 10 dans ma team", enigmeDisponible.values().stream().collect(Collectors.toList()).subList(0,2),60));
        escapeGamesDisponible.put("Sherlock",new EscapeGame("Sherlock", enigmeDisponible.values().stream().collect(Collectors.toList()), 60));
        escapeGamesDisponible.put("EscapAtor",new EscapeGame("EscapAtor", enigmeDisponible.values().stream().collect(Collectors.toList()).subList(1,4), 60));
    }


    public boolean createNewEngime(Enigme enigme){
        if (enigmeDisponible.containsKey(enigme.getName()))
            return false;
        enigmeDisponible.put(enigme.getName(),enigme);
        return true;
    }

    public int createNewPartie(Partie partie){
        parties.put(++id,partie);
        return id;
    }

    public EscapeGame getEscapeGame(String name){
        return escapeGamesDisponible.get(name);
    }

    public List<EscapeGame> getEscapeGamesDisponible() {
        return escapeGamesDisponible.values().stream().collect(Collectors.toList());
    }

    public List<Enigme> getEnigmeDisponible() {
        return enigmeDisponible.values().stream().collect(Collectors.toList());
    }

    public Map<Integer,Partie> getParties() {
        return parties;
    }

    public Partie getPartieByID(int id){
        return parties.get(id);
    }
}
