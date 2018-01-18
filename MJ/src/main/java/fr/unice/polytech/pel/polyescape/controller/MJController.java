package fr.unice.polytech.pel.polyescape.controller;

import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import fr.unice.polytech.pel.polyescape.Transmission.TypeRequest;
import fr.unice.polytech.pel.polyescape.model.communication.ClientMJ;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import org.controlsfx.control.Notifications;
import org.glassfish.tyrus.client.ClientManager;
import org.json.JSONObject;

import javax.websocket.DeploymentException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

public class MJController {

    private Scene scene;
    Boolean finDuJeu = false;
    private StackPane topPane;
    private ProgressBar progressBar;
    private ProgressIndicator progressIndicatorTime;
    private Label choicePlayer;
    private ComboBox listPlayer;
    private HBox timeHB;
    private StackPane bottomPane;
    private Button btnAnswer;
    private TextArea answerField;
    private Label remindedTime;
    private Label progressLabel;
    private Text teamName;
    private Text escapeGameName;
    private String selectedPlayer;

    private int hour;
    private int minute;
    private int givenMinutes;

    public MJController(Scene scene, StackPane topPane, ProgressBar progressBar, Label choicePlayer, ComboBox listPlayer, HBox timeHB, StackPane bottomPane, Button btn, Label progressLabel, ProgressIndicator timeIndicator) throws URISyntaxException, DeploymentException, InterruptedException {
        //il faut ici recuperer les hours et minutes en instanciant un client et envoyant des requetes
        this.progressIndicatorTime = timeIndicator;
        this.scene = scene;
        this.topPane = topPane;
        this.progressBar = progressBar;
        this.choicePlayer = choicePlayer;
        this.listPlayer = listPlayer;
        this.timeHB = timeHB;
        this.bottomPane = bottomPane;
        this.btnAnswer = btn;
        this.progressLabel = progressLabel;
        this.remindedTime = ((Label) (timeHB.getChildren().get(0)));
        this.answerField = ((TextArea) (bottomPane.getChildren().get(0)));
        this.selectedPlayer = "";
        this.hour = 0;
        this.minute = 2;
        this.givenMinutes = 2;
        this.teamName = ((Text) (topPane.getChildren().get(1)));
        this.escapeGameName = ((Text) (topPane.getChildren().get(0)));
        JSONObject jsonObject = new JSONObject().put(JsonArguments.REQUEST.toString(), TypeRequest.GET_PARTIES);
        String request = jsonObject.toString();
        makeRequest(request);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        display();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }, 60000, 60000);
        initialize();
    }

    public void initialize() {
        firstRemainingTime();
        ObservableList<String> listOfPlayers = FXCollections.observableArrayList();
        listOfPlayers.add("Pierre");
        listOfPlayers.add("Theo");
        listOfPlayers.add("Gaetan");
        listOfPlayers.add("Eric");
        this.listPlayer.setItems(listOfPlayers);

        this.btnAnswer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                envoieIndice();
            }
        });
        this.listPlayer.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                selectedPlayer = listPlayer.getValue().toString();
                ((Text) (topPane.getChildren().get(2))).setText(selectedPlayer);
            }
        });
    }

    public void envoieIndice() {
        try {
            Media hit = new Media(getClass().getClassLoader().getResource("sound/notification.wav").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("envoie");
        Notifications.create().title("Nouvelle notif").text("Joueur " + selectedPlayer + " a demandé de l'aide").darkStyle().position(Pos.TOP_LEFT).showWarning();
        System.out.println(answerField.getText());
        answerField.setText("");
    }

    public void display() throws InterruptedException {
        String tobeDisplayed = "";
        if (this.minute == 0 && this.hour != 0) {
            this.hour--;
            this.minute = 59;
        } else if (this.minute > 0) {
            this.minute--;
        }
        if (this.hour == 0 && this.minute == 0) {
            finDuJeu = true;
            Media hit = new Media(getClass().getClassLoader().getResource("sound/sonnerie.wav").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        }
        if (this.hour == 0 && this.minute == 1) {//A MODIF
            Notifications.create().title("Temps faible").text("Temps restant :1 minute !").darkStyle().position(Pos.CENTER).showWarning();
            Media hit = new Media(getClass().getClassLoader().getResource("sound/1min.mp3").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        }
        if (this.minute < 10 && this.hour < 10) tobeDisplayed = "0" + this.hour + " : " + "0" + this.minute;
        else if (this.minute < 10 && !(this.hour < 10)) tobeDisplayed = this.hour + " : " + "0" + this.minute;
        else if (!(this.minute < 10) && this.hour < 10) tobeDisplayed = "0" + this.hour + " : " + this.minute;
        else if (!(this.minute < 10) && !(this.hour < 10)) tobeDisplayed = this.hour + " : " + this.minute;
        remindedTime.setText(tobeDisplayed);
        progressIndicatorTime.setProgress(((double) (hour * 60 + minute)) / ((double) (givenMinutes)));
    }

    private void firstRemainingTime() {
        if (this.minute < 10 && this.hour < 10) this.remindedTime.setText("0" + this.hour + " : " + "0" + this.minute);
        else if (this.minute < 10 && !(this.hour < 10))
            this.remindedTime.setText(this.hour + " : " + "0" + this.minute);
        else if (!(this.minute < 10) && this.hour < 10)
            this.remindedTime.setText("0" + this.hour + " : " + this.minute);
        else if (!(this.minute < 10) && !(this.hour < 10)) this.remindedTime.setText(this.hour + " : " + this.minute);
    }

    public void makeRequest(String request) throws URISyntaxException, DeploymentException, InterruptedException {
//        System.out.println(request);
//        System.out.println(new JSONObject(request).getString(JsonArguments.REPONSE.toString()));
//        if (new JSONObject(request).getString(JsonArguments.REPONSE.toString()).equals("infos")){
//            envoieIndice();
//        }
        request = "{\"request\":\"HELP\",\"idpartie\":2,\"username\":\"bob\",\"enigme\":\"Dans quel rayon se trouve le livre \\\"Le code pour les nuls\\\"\"}";
        ClientMJ clientMJ = new ClientMJ(request, progressIndicatorTime, teamName, escapeGameName, listPlayer, this);
        ClientManager client = ClientManager.createClient();
        client.connectToServer(clientMJ, new URI("ws://localhost:15555/websockets/gameserver"));
    }
}