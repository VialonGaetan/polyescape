package fr.unice.polytech.pel.polyescape.Data;

import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class Equipe implements Serialize{

    private String name;
    private Map<Joueur, List<Enigme>> association;
    private Map<Joueur, Boolean> readyToStart;

    public Equipe(String name, Joueur joueurs){
        this.name = name;
        readyToStart = new HashMap<>();
        association = new HashMap<>();
        readyToStart.put(joueurs,false);
    }


    void attributeEnigme(EscapeGame escapeGame){
        for (Joueur joueur: readyToStart.keySet()) {
            association.put(joueur, new ArrayList<>());
        }
        for (Enigme enigme : escapeGame.getEnigmes()) {
            association.get(getRandomJoueur()).add(new Enigme(enigme.getName(),enigme.getDescription(),enigme.getReponse()));
        }
    }

    public int numberOfPlayer(){
        return readyToStart.keySet().size();
    }

    private Joueur getRandomJoueur() {
        return association.keySet().stream().findFirst().get();
    }

    public List<Enigme> getEnigmesOfaPlayer(Joueur joueur){
        return association.get(joueur);
    }

    public Optional<Enigme> getCurrentEnigmesOfaPlayer(Joueur joueur){
        return association.get(joueur).stream().filter(enigme -> !enigme.isResolve()).findFirst();
    }

    public boolean joinPartie(Joueur joueur){
        if (readyToStart.keySet().size()>=4)
            return false;
        else {
            readyToStart.put(joueur,false);
            return true;
        }
    }

    public String getName(){
        return name;
    }

    public boolean setJoueurReadyOrNot(Joueur joueur, Boolean ready){
        readyToStart.replace(joueur,ready);
        return readyToStart.values().stream().allMatch(aBoolean -> aBoolean);
    }


    @Override
    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JsonArguments.NOM.toString(),this.name);
        JSONArray jsonArray = new JSONArray();
        for (Joueur joueur: association.keySet()) {
            JSONArray jsonArrayEnigme = new JSONArray();
            jsonArray.put(joueur.toJson());
            for (Enigme enigme : association.get(joueur)) {
                jsonArrayEnigme.put(enigme.toJson());
            }
            jsonArray.put(jsonArrayEnigme);
        }
        jsonObject.put(JsonArguments.JOUEURS.toString(),jsonArray);
        return jsonObject;
    }

}
