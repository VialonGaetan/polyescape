package fr.unice.polytech.pel.polyescape;

import fr.unice.polytech.pel.polyescape.data.*;

import javax.websocket.Session;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public class Gestionnaire {

    private static Gestionnaire ourInstance = new Gestionnaire();
    private final Map<String,EscapeGame> escapeGamesDisponible;
    private final Map<String,Enigme> enigmeDisponible;
    private final Map<String, FinalEnigme> finalEnigmeMap;
    private final Map<Integer,Partie> parties;
    private int id = 0;
    private Session sessionMG;

    public static Gestionnaire getInstance() {
        return ourInstance;
    }

    private Gestionnaire() {
        escapeGamesDisponible = new HashMap<>();
        enigmeDisponible = new HashMap<>();
        parties = new HashMap<>();
        finalEnigmeMap = new HashMap<>();
        enigmeDisponible.put("Resoud l'operation",new Enigme("Resoud l'operation","Chaise * Tables =","42"));
        enigmeDisponible.put("Loi de Newton",new Enigme("Loi de Newton","En quelle année a été ecrit la deuxieme loi de Newton","1686"));
        enigmeDisponible.put("Le code pour les nuls",new Enigme("Le code pour les nuls","Dans quel rayon se trouve le livre \"Le code pour les nuls\" au learning centre","Informatique"));
        enigmeDisponible.put("Upside down",new Enigme("Upside down","Quelle salle se trouve juste au dessus de la salle O+124","O+225"));
        enigmeDisponible.put("Le mot caché",new Enigme("Le mot caché","4 images forment un mot","escape"));
        enigmeDisponible.put("Le garde du château", new Enigme("Le garde du château","Pour entrer dans le château, il est nécessaire de connaître le mot de passe. Alors vous observez et écoutez les gens qui se présentent à la porte. Un enfant arrive ; le garde lui dit \"5\", l'enfant répond \"4\" et le garde le laisse entrer. Une femme se présente ; le garde lui dit \"6\", elle répond \"3\" et passe. Un homme paraît ; le garde lui dit \"4\", l'homme répond \"6\" et entre. C'est votre tour. Le garde vous dit \"7\". \n" + "Que répondez-vous ?","4"));
        enigmeDisponible.put("A la piscine", new Enigme("A la piscine","Sur le couloir n°1, Paul part en crawl côté plongeoir, mais avec un départ non plongé.\n" +
                "\n" +
                "Sur le couloir n°2, Virginie part en même temps, en brasse coulée, du côté du petit bain. Ils nagent à vitesse constantes. Il se croisent une première fois à 5 m de l'extrémité de la piscine, côté petit bain. Puis tous deux, l'un après l'autre, font demi-tour et repartent l'un vers l'autre en conservant la même allure. Ils se croisent une deuxième fois à 2,5 m de l'extrémité côté plongeoir. \n" +
                "\n" +
                "Quelle est la longueur de la piscine ?", "12.5"));
        escapeGamesDisponible.put("Que des numeros 10 dans ma team",new EscapeGame("Que des numeros 10 dans ma team", " A bord d’un paquebot flambant neuf, une avarie survient. L’équipage vous donne pour consigne de quitter le navire. Panique à bord ! Les passagers se bousculent et se poussent pour atteindre au plus vite les canots de sauvetage. Dans la cohue, vous prenez un mauvais coup et perdez connaissance. A votre réveil, il ne reste plus que vous à bord…",enigmeDisponible.values().stream().collect(Collectors.toList()).subList(0,2),60));
        escapeGamesDisponible.put("Sherlock",new EscapeGame("Sherlock", "Londres,\n" + "Plongez dans l’appartement du célèbre détective pour tenter d’en percer tous les mystères.",enigmeDisponible.values().stream().collect(Collectors.toList()), 60));
        escapeGamesDisponible.put("Lisa",new EscapeGame("Lisa", "Le professeur Franck Tipler a toujours convoité le trésor secret de sa famille. Après de nombreux échecs, il comprit qu’il n’y arriverai pas seul.\n" +
                "Pour l’aider dans sa tâche, il a créé “Lisa”.",enigmeDisponible.values().stream().collect(Collectors.toList()).subList(1,4), 60));
        escapeGamesDisponible.put("TOUT",new EscapeGame("TOUT","2016. Un signal d’un vaisseau, dont la mission dans les années 90 était de faire des prélèvement de matière sur la lune Europa mais qui s’était perdu lors du voyage retour, est capté. Les derniers calculs font état d’un crash sur Mercure dans 60 minutes. Votre équipe est envoyée à bord pour tenter de modifier sa trajectoire et de récupérer des échantillons scientifiques à bord depuis 20 ans",new ArrayList<>(enigmeDisponible.values()), 120));
        finalEnigmeMap.put("Colle les tous",new FinalEnigme("Colle les tous", "Recupere les indices de tout le monde et assemble les chiffres pour former le mot de passe final","1234"));
        finalEnigmeMap.put("Retrouve la phrase",new FinalEnigme("Retrouve la phrase", "Recupere les indices de tout le monde et assemble les mots pour former le mot de passe final","pokemon"));
        finalEnigmeMap.put("Colle les tous",new FinalEnigme("Colle les tous", "Recupere les indices de tout le monde et assemble les chiffres pour former le mot de passe final","1234"));
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

    public void setSessionMG(Session sessionMG) {
        this.sessionMG = sessionMG;
    }

    public Session getSessionMG() {
        return sessionMG;
    }

    public FinalEnigme getRandomFinalEnigme(){
        return finalEnigmeMap.values().toArray(new FinalEnigme[0])[new Random().nextInt(finalEnigmeMap.values().size())];
    }
}
