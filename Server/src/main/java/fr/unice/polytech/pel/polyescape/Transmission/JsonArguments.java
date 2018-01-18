package fr.unice.polytech.pel.polyescape.Transmission;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public enum  JsonArguments {
    REQUEST("request"),
    TYPE("type"),
    SOLO("solo"),
    NOM("nom"),
    INFOS("infos"),
    REPONSE("reponse"),
    TEMPS("temps"),
    EQUIPE("equipe"),
    ENIGMES("enigmes"),
    ESCAPEGAMES("escapegames"),
    ESCAPEGAME("escapegame"),
    USERNAME("username"),
    OK("ok"),
    KO("ko"),
    FINISH("finish"),
    SCORE("score"),
    JOUEURS("joueurs"),
    PARTIEENCOURS("partieencours"),
    IDPARTIE("idpartie"),
    ISRESOLVE("isresolve");


    /**
     * the string associated to an argument
     */
    private String name;

    /**
     * init the string
     * @param name, String value
     */
    JsonArguments(String name)
    {
        this.name = name;
    }

    /**
     * return the string associated to the argument
     * @return a String value
     */
    @Override
    public String toString()
    {
        return name;
    }
}
