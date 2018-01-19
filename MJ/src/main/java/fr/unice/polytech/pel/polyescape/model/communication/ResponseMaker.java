package fr.unice.polytech.pel.polyescape.model.communication;

import org.json.JSONObject;

public class ResponseMaker {

    private String indice;
    private String username;
    private String idGame;

    public ResponseMaker(String indice, String selectedPlayer, String idGame) {
        this.indice = indice;
        this.username = selectedPlayer;
        this.idGame = idGame;
    }

    public String getAnswer() {
        return answerInJson().toString();
    }

    public JSONObject answerInJson() {
        JSONObject jsonObject = new JSONObject().put("request","INDICE");
        jsonObject.put("description",indice);
        jsonObject.put("username",this.username);
        jsonObject.put("idGame",this.idGame);
        return jsonObject;
    }
}
