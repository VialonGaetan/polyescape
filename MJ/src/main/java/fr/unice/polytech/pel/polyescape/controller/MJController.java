package fr.unice.polytech.pel.polyescape.controller;

import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import fr.unice.polytech.pel.polyescape.Transmission.TypeRequest;
import fr.unice.polytech.pel.polyescape.model.communication.AdressBook;
import fr.unice.polytech.pel.polyescape.model.communication.ClientMJ;
import fr.unice.polytech.pel.polyescape.model.communication.ResponseMaker;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.controlsfx.control.Notifications;
import org.glassfish.tyrus.client.ClientManager;
import org.json.JSONObject;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

public class MJController {

    private Scene scene;
    Boolean finDuJeu = false;

    private StackPane topPane;
    private HBox timeHB;
    private StackPane bottomPane;

    private ProgressIndicator teamProgress;
    private ProgressIndicator progressIndicatorTime;
    private ComboBox listPlayer;

    private Label choicePlayer;
    private Label remindedTime;
    private Label progressLabel;

    private Text teamName;
    private Text escapeGameName;
    private Text descriptionEnigma;


    private Button btnAnswer;
    private TextArea answerField;

    private String selectedPlayer;

    private int idPartie;
    private int hour;
    private int minute;
    private int givenMinutes;

    private ResponseMaker responseMaker;

    public MJController(Scene scene, StackPane topPane, ProgressIndicator teamProgress, Label choicePlayer, ComboBox listPlayer, HBox timeHB, StackPane bottomPane, Button btn, Label progressLabel, ProgressIndicator timeIndicator, Text descriptionEnigma) throws URISyntaxException, DeploymentException, InterruptedException, IOException {
        this.progressIndicatorTime = timeIndicator;
        this.scene = scene;
        this.topPane = topPane;
        this.teamProgress = teamProgress;
        this.teamProgress.setProgress(0.1);
        this.choicePlayer = choicePlayer;
        this.listPlayer = listPlayer;
        this.timeHB = timeHB;
        this.bottomPane = bottomPane;
        this.btnAnswer = btn;
        this.progressLabel = progressLabel;
        this.remindedTime = ((Label) (timeHB.getChildren().get(0)));
        this.answerField = ((TextArea) (bottomPane.getChildren().get(0)));
        this.selectedPlayer = "";
        this.descriptionEnigma = descriptionEnigma;
        this.teamName = ((Text) (topPane.getChildren().get(1)));
        this.escapeGameName = ((Text) (topPane.getChildren().get(0)));
        JSONObject jsonObject = new JSONObject().put(JsonArguments.REQUEST.toString(), TypeRequest.GET_PARTIES);
        String request = jsonObject.toString();
        makeRequest(request);
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
                try {
                    envoieIndice();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        this.listPlayer.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                selectedPlayer = listPlayer.getValue().toString();
                ((Text) (topPane.getChildren().get(2))).setText(selectedPlayer);
                if (!AdressBook.getInstance().getPlayersEnigma().containsKey(selectedPlayer)){
                    descriptionEnigma.setText("Le joueur n'a pas encore eu de problèmes particuliers");
                }else{
                    descriptionEnigma.setText("Dernière enigme rencontrée :\n"+AdressBook.getInstance().getPlayersEnigma().get(selectedPlayer));
                }
            }
        });
    }

    public void envoieIndice() throws IOException {
        if (this.teamName.getText().equals("")) return;
        try {
            Media hit = new Media(getClass().getClassLoader().getResource("sound/notification.wav").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Notifications.create().title("Indice envoyé").text("Indice envoyé au joueur " + selectedPlayer).darkStyle().position(Pos.TOP_LEFT).showWarning();
        responseMaker = new ResponseMaker(answerField.getText(), selectedPlayer, ""+this.idPartie);
        answerField.setText("");
        updateListPlayer(selectedPlayer, false);
        AdressBook.getInstance().getServerSession().getBasicRemote().sendText(responseMaker.getAnswer());
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
        if (this.hour == 0 && this.minute == 1) {
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
        this.minute=this.givenMinutes%60;
        this.hour=this.givenMinutes/60;
        if (this.minute < 10 && this.hour < 10) this.remindedTime.setText("0" + this.hour + " : " + "0" + this.minute);
        else if (this.minute < 10 && !(this.hour < 10))
            this.remindedTime.setText(this.hour + " : " + "0" + this.minute);
        else if (!(this.minute < 10) && this.hour < 10)
            this.remindedTime.setText("0" + this.hour + " : " + this.minute);
        else if (!(this.minute < 10) && !(this.hour < 10)) this.remindedTime.setText(this.hour + " : " + this.minute);
    }

    public void makeRequest(String request) throws URISyntaxException, DeploymentException, InterruptedException, IOException {
        ClientMJ clientMJ = new ClientMJ(request, progressIndicatorTime, teamName, escapeGameName, listPlayer, this,this.descriptionEnigma, this.teamProgress);
        ClientManager client = ClientManager.createClient();
        client.connectToServer(clientMJ, new URI("ws://localhost:15555/websockets/gameserver"));
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

    public void setIdPartie(int idPartie) {
        this.idPartie = idPartie;
    }

    public void startTime(int timeInMinutes){
        this.givenMinutes = timeInMinutes;
        firstRemainingTime();
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
    }

    public void startProgress(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    JSONObject jsonObject = new JSONObject().put(JsonArguments.REQUEST.toString(), TypeRequest.GET_PARTIES);
                    String request = jsonObject.toString();
                    try {
                        AdressBook.getInstance().getServerSession().getBasicRemote().sendText(request);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }, 5000, 5000);
    }
}
