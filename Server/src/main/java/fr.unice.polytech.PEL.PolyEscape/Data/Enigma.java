package fr.unice.polytech.PEL.PolyEscape.Data;

public class Enigma {

    String description;
    String response;

    public Enigma(String description, String response) {
        this.description = description;
        this.response = response;
    }

    public String getDescription() {
        return description;
    }

    public String getResponse() {
        return response;
    }
}
