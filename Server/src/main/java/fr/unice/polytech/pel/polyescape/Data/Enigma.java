package fr.unice.polytech.pel.polyescape.Data;

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
