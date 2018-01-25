package fr.unice.polytech.pel.polyescape.data;

/**
 * @author Gaetan Vialon
 * Created the 24/01/2018.
 */
public class Indice {

    private boolean isDiscover;
    private String indice;


    public Indice(String indice) {
        this.indice = indice;
        isDiscover = false;
    }

    public void discoverIndice(){
        isDiscover = true;
    }

    public boolean isDiscover() {
        return isDiscover;
    }

    public String getIndice() {
        return indice;
    }
}
