package fr.unice.polytech.pel.polyescape.Transmission;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public enum  JsonArguments {
    NOM("nom"),
    DESCRIPTION("description"),
    REPONSE("reponse"),
    TEMPS("temps"),
    ENIGMES("enigmes"),
    ESCAPEGAMES("escapegames"),
    ESCAPEGAME("escapegame"),
    USERNAME("username"),
    OK("ok"),
    KO("ko"),
    ID("id");


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
