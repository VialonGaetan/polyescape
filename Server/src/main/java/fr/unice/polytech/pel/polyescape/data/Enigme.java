package fr.unice.polytech.pel.polyescape.data;

import fr.unice.polytech.pel.polyescape.transmission.JsonArguments;
import org.json.JSONObject;

/**
 * @author Gaetan Vialon
 * Created the 12/01/2018.
 */
public class Enigme implements Serialize {

    private String name;
    private String description;
    private String reponse;
    private Boolean isResolve = false;

    public Enigme(String name, String description, String reponse) {
        this.name = name;
        this.description = description;
        this.reponse = reponse;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getReponse() {
        return reponse;
    }

    public Boolean checkAnswer(String solution){
        if (reponse.toLowerCase().equals(solution.toLowerCase()))
            return isResolve = true;
        else
            return false;
    }

    public Boolean isResolve() {
        return isResolve;
    }

    @Override
    public JSONObject toJson(){
        return new JSONObject().put(JsonArguments.NOM.toString(),name)
                .put(JsonArguments.INFOS.toString(),description)
                .put(JsonArguments.REPONSE.toString(),reponse)
                .put(JsonArguments.ISRESOLVE.toString(),isResolve);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (this.getClass() != o.getClass())
            return false;

        Enigme enigme = (Enigme) o;
        return this.name.equals(enigme.name) && this.description.equals(enigme.description) && this.reponse.equals(enigme.reponse);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (reponse != null ? reponse.hashCode() : 0);
        return result;
    }
}
