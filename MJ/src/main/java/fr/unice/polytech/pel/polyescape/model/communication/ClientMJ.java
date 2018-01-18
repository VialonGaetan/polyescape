package fr.unice.polytech.pel.polyescape.model.communication;

import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;
import org.json.JSONObject;

import javax.websocket.*;
import java.io.IOException;
import java.util.logging.Logger;

@ClientEndpoint
public class ClientMJ {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private final String message;
    private String answer;
    private DataParser dataParser;
    private ProgressIndicator progressIndicatorTime;
    private javafx.scene.text.Text escapeGameName;
    private javafx.scene.text.Text teamName;
    private ComboBox listPlayer;
    private Boolean firstTime = true;
    private int timeInMinute;

    public ClientMJ(String message, ProgressIndicator progressIndicator, Text teamNameText, Text escapeGameName, ComboBox listPlayer, int timeInMinute) {
        this.message = message;
        this.answer = "";
        this.dataParser = new DataParser(message);
        this.listPlayer = listPlayer;
        this.progressIndicatorTime = progressIndicator;
        this.escapeGameName = escapeGameName;
        this.teamName = teamNameText;
        this.timeInMinute= timeInMinute;
    }

    public ClientMJ(String message) {
        this.message = message;
    }

    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connected ... " + session.getId());
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(Session session) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        dataParser = new DataParser(message);
        this.answer = message;
        System.out.println(this.answer);
        switch (new JSONObject(message).getString(JsonArguments.REPONSE.toString())) {
            case "help": {
                System.out.println("protocol help");
                protocolHelp();
                break;
            }
            case "infos": {
                System.out.println("protocol infos");
                protocolInfos();
                break;
            }
            default: {
                System.out.println("default");
            }
        }
        return message;
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s close because of %s", session.getId(), closeReason));
    }

    public String getAnswer() {
        return answer;
    }

    private void protocolHelp() {
        System.out.println("demande d'aide");
    }

    private void protocolInfos() {
        if (firstTime){
            this.timeInMinute = this.dataParser.getTime();
            this.listPlayer.setItems(this.dataParser.getPlayers());
            this.escapeGameName.setText(this.dataParser.getTeamName());
            firstTime = false;
        }else{
            return;
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public String getMessage() {
        return message;
    }

    public DataParser getDataParser() {
        return dataParser;
    }

    public ProgressIndicator getProgressIndicatorTime() {
        return progressIndicatorTime;
    }

    public Text getEscapeGameName() {
        return escapeGameName;
    }

    public Text getTeamName() {
        return teamName;
    }

    public ComboBox getListPlayer() {
        return listPlayer;
    }

    public Boolean getFirstTime() {
        return firstTime;
    }

    public int getTimeInMinute() {
        return timeInMinute;
    }
}