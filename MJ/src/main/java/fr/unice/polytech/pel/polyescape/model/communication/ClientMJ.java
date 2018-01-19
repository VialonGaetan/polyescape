package fr.unice.polytech.pel.polyescape.model.communication;

import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import fr.unice.polytech.pel.polyescape.controller.MJController;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
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
    private int timeInMinute = 0;
    MJController mjController;
    private Boolean needHelp = false;
    private String currentEnigma = "";
    private String nameOfThePlayer = "";


    public ClientMJ(String message, ProgressIndicator progressIndicator, Text teamNameText, Text escapeGameName, ComboBox listPlayer, MJController mjController) {
        this.message = message;
        this.answer = "";
        this.dataParser = new DataParser(message);
        this.listPlayer = listPlayer;
        this.progressIndicatorTime = progressIndicator;
        this.escapeGameName = escapeGameName;
        this.teamName = teamNameText;
        this.mjController = mjController;
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
    public void onMessage(String message, Session session) throws InterruptedException {
        System.out.println(message);
        dataParser = new DataParser(message);
        this.answer = message;
        JSONObject jsonObject = new JSONObject(message);
        if (new JSONObject(message).getString(JsonArguments.REPONSE.toString()).equals("infos")) {
            System.out.println("protocol infos");
            protocolInfos();
            this.needHelp = true;
            return;
        }
        if (jsonObject.getString(JsonArguments.REPONSE.toString()).equals("HELP")) {
            System.out.println("on est bien la");
            this.currentEnigma = jsonObject.getString("enigme");
            this.nameOfThePlayer = jsonObject.getString("username");
            protocolHelp();
        }
        return;
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
        try {
            Media hit = new Media(getClass().getClassLoader().getResource("sound/aide.mp3").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void protocolInfos() {
        if (firstTime) {
            this.timeInMinute = this.dataParser.getTime();
            this.listPlayer.setItems(this.dataParser.getPlayers());
            this.escapeGameName.setText(this.dataParser.getTeamName());
            updateListPlayer(this.nameOfThePlayer, false);
            firstTime = false;
        }
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

    public Boolean getNeedHelp() {
        return needHelp;
    }

    public String getCurrentEnigma() {
        return currentEnigma;
    }

    public String getNameOfThePlayer() {
        return nameOfThePlayer;
    }

    public void updateListPlayer(String username, boolean isHelped) {
        listPlayer.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                final ListCell<String> cell = new ListCell<String>() {
                    {
                        super.setPrefWidth(100);
                    }

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                            if (item.contains(username)) {
                                if (isHelped) {
                                    setTextFill(Color.GREEN);
                                } else {
                                    setTextFill(Color.RED);
                                }
                            }
                        } else {
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
    }
}