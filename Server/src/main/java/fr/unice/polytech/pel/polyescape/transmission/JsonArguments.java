package fr.unice.polytech.pel.polyescape.transmission;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public enum  JsonArguments {
    REQUEST("request"),
    TEAMNAME("teamname"),
    NOM("nom"),
    INFOS("infos"),
    REPONSE("reponse"),
    TEMPS("temps"),
    EQUIPE("equipe"),
    ENIGMES("enigmes"),
    ENIGME("enigme"),
    ESCAPEGAMES("escapegames"),
    ESCAPEGAME("escapegame"),
    USERNAME("username"),
    OK("ok"),
    KO("ko"),
    FINISH("finish"),
    NOTYET("notyet"),
    SCORE("score"),
    JOUEURS("joueurs"),
    PARTIEENCOURS("partieencours"),
    PARTIEATTENTE("partieattente"),
    IDPARTIE("idpartie"),
    ISRESOLVE("isresolve"),
    ACTUALISE("actualise"),
    TOTAL("total"),
    ACTUAL("actual"),
    SUCCESS("success"),
    INDICE("indice"),
    INDICES("indices"),
    READY("ready");


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
