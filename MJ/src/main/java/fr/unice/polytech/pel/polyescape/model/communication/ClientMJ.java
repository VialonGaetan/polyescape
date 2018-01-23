package fr.unice.polytech.pel.polyescape.model.communication;

import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import fr.unice.polytech.pel.polyescape.controller.MJController;
import fr.unice.polytech.pel.polyescape.model.parser.DataParser;
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
    private DataParser dataParser;
    private ProgressIndicator progressIndicatorTime;
    private ProgressIndicator progressEnigma;
    private javafx.scene.text.Text escapeGameName;
    private javafx.scene.text.Text teamName;
    private ComboBox listPlayer;
    private Boolean firstTime = true;
    private int timeInMinute = 0;
    private MJController mjController;
    private String currentEnigma = "";
    private String nameOfThePlayer = "";
    private String idPartie;
    private Text descriptionEnigma;


    public ClientMJ(String message, ProgressIndicator progressIndicator, Text teamNameText, Text escapeGameName, ComboBox listPlayer, MJController mjController, Text descriptionEnigma, ProgressIndicator progressEnigma) throws IOException {
        this.message = message;
        this.dataParser = new DataParser(message);
        this.listPlayer = listPlayer;
        this.progressIndicatorTime = progressIndicator;
        this.escapeGameName = escapeGameName;
        this.teamName = teamNameText;
        this.mjController = mjController;
        this.descriptionEnigma = descriptionEnigma;
        this.progressEnigma = progressEnigma;
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
    public void onMessage(String message, Session session) throws InterruptedException, IOException {
        System.out.println(message);
        dataParser = new DataParser(message);
        JSONObject jsonObject = new JSONObject(message);
        if (new JSONObject(message).getString(JsonArguments.REPONSE.toString()).equals("infos")) {
            AdressBook.getInstance().setServerSession(session);
            protocolInfos();
            return;
        }
        if (jsonObject.getString(JsonArguments.REPONSE.toString()).equals("HELP")) {
            this.currentEnigma = jsonObject.getString("enigme");
            this.nameOfThePlayer = jsonObject.getString("username");
            this.idPartie = ""+jsonObject.getInt("idGame");
            this.mjController.setIdPartie(jsonObject.getInt("idGame"));
            AdressBook.getInstance().updateEnigma(nameOfThePlayer, currentEnigma);
            AdressBook.getInstance().updateSessions(this.nameOfThePlayer,session);
            protocolHelp(this.nameOfThePlayer);
        }
        return;
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s close because of %s", session.getId(), closeReason));
    }

    private void protocolHelp(String nameOfThePlayer) {
        try {
            Media hit = new Media(getClass().getClassLoader().getResource("sound/aide.mp3").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
            System.out.println(nameOfThePlayer);
            updateListPlayer(nameOfThePlayer,true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void protocolInfos() {
        if (firstTime) {
            this.timeInMinute = this.dataParser.getTime();
            this.mjController.startTime(this.timeInMinute);
            this.listPlayer.setItems(this.dataParser.getPlayers());
            this.escapeGameName.setText(this.dataParser.getTeamName());
            firstTime = false;
        }
        if (!firstTime){
            mjController.startProgress();
            this.progressEnigma.setProgress(this.dataParser.getProgress());
        }
    }

    public void updateListPlayer(String username, boolean askForHelp) {
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
                                if (askForHelp) {
                                    setTextFill(Color.RED);
                                } else {
                                    setTextFill(Color.GREEN);
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