package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

public class MJController{

    private Scene scene;
    private StackPane topPane;
    private ProgressBar progressBar;
    private Label choicePlayer;
    private ComboBox listPlayer;
    private HBox timeHB;
    private StackPane bottomPane;
    private Button btnAnswer;

    private String selectedPlayer;

    public MJController(Scene scene, StackPane topPane, ProgressBar progressBar, Label choicePlayer, ComboBox listPlayer, HBox timeHB, StackPane bottomPane, Button btn){
        this.scene=scene;
        this.topPane = topPane;
        this.progressBar = progressBar;
        this.choicePlayer = choicePlayer;
        this.listPlayer = listPlayer;
        this.timeHB = timeHB;
        this.bottomPane = bottomPane;
        this.btnAnswer = btn;

        this.selectedPlayer ="";
        initialize();
    }

    public void initialize() {
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
                ((Text)(topPane.getChildren().get(2))).setText(selectedPlayer);
            }
        });
    }

    public void envoieIndice(){
        try {
            Media hit = new Media(getClass().getClassLoader().getResource("sounds/notification.wav").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }        System.out.println("envoie");
    }

    public void setTimeAvancement(){

    }
}
