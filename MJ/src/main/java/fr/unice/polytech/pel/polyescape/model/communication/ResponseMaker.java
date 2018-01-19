package fr.unice.polytech.pel.polyescape.model.communication;

import org.json.JSONObject;

public class ResponseMaker {

    private String indice;

    public ResponseMaker(String indice) {
        this.indice = indice;
    }

    public String getAnswer() {
        return answerInJson().toString();
    }

    public JSONObject answerInJson() {
        JSONObject jsonObject = new JSONObject().put("request","INDICE");
        jsonObject.put("description",indice);
        return jsonObject;
    }
}
