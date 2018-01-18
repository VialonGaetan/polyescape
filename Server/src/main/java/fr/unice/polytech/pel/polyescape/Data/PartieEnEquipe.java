package fr.unice.polytech.pel.polyescape.Data;

import java.util.List;

/**
 * @author Gaetan Vialon
 * Created the 18/01/2018.
 */
public class PartieEnEquipe extends Partie implements Serialize {

    private String teamName;
    private Equipe equipe;

    public PartieEnEquipe(EscapeGame escapeGame, Joueur joueur, String teamName) {
        super();
        this.escapeGame = escapeGame;
        equipe = new Equipe(teamName,joueur);
    }

    @Override
    public int numberOfPlayer(){
        return equipe.numberOfPlayer();
    }

    @Override
    public boolean joinPartie(Joueur joueur){
        if (hasStart)
            return false;
        else {
            return equipe.joinPartie(joueur);
        }
    }

    @Override
    protected void attributeEnigme() {
        equipe.attributeEnigme(escapeGame);
    }

    @Override
    public List<Enigme> getEnigmesOfaPlayer(Joueur joueur){
        return equipe.getEnigmesOfaPlayer(joueur);
    }
}
