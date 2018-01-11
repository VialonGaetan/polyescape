package model;

import controller.MJController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;

public class ScreenMJ extends Stage{

    private ProgressBar progressBar;
    private MJController control;

    private StackPane topPane;

    private Pane basePane;
    private Group root ;
    private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    private final int HAUTEUR = (int)dimension.getHeight();
    private final int LARGEUR  = (int)dimension.getWidth();
    Text teamName;
    Text escapeGameName;
    Text playerName;
    Text remindedTime;
    ComboBox listPlayer;

    public ScreenMJ(){

    }

    public MJController init(){
        //Definition de base
        basePane = new Pane();
        root = new Group();
        Scene scene = new Scene(root,LARGEUR, HAUTEUR);

        //Definition partie haute
        topPane = new StackPane();
        topPane.setPrefSize(LARGEUR,HAUTEUR/15);
        escapeGameName = new Text("Escape game A");
        teamName=new Text("Equipe A");
        playerName = new Text("Nom du joueur");

        topPane.getChildren().addAll(escapeGameName,teamName,playerName);

        topPane.setAlignment(escapeGameName, Pos.TOP_LEFT);
        topPane.setAlignment(teamName, Pos.TOP_CENTER);
        topPane.setAlignment(playerName,Pos.TOP_RIGHT);
        basePane.getChildren().addAll(topPane);

        //definition de la progressBar
        progressBar = new ProgressBar();
        progressBar = new ProgressBar();
        progressBar.setProgress(0.5);
        progressBar.setLayoutX(LARGEUR/2 - 47);
        progressBar.setLayoutY(HAUTEUR/15);

        basePane.getChildren().addAll(progressBar);

        //definition de la liste des joueurs et du temps restant (3eme couche)
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Option 1",
                        "Option 2",
                        "Option 3"
                );
        listPlayer = new ComboBox(options);
        listPlayer.setLayoutX(0);
        listPlayer.setLayoutY(2*HAUTEUR/15);

        remindedTime = new Text("1:00:00");
        remindedTime.setLayoutX(LARGEUR/2 - 47);
        remindedTime.setLayoutY(2*HAUTEUR/15);


        basePane.getChildren().addAll(listPlayer, remindedTime);


        //Génération de la scene
        root.getChildren().add(basePane);
        this.setTitle("FENETRE DU MJ");
        this.setScene(scene);
        this.show();
        //control = new MJController(scene,btn);
        return control;
    }

}