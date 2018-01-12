package model;

import controller.MJController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;

public class ScreenMJ extends Stage{

    private ProgressBar progressBar;
    private ProgressIndicator timeIndicator;
    private StackPane bottomPane;

    private MJController control;

    private StackPane topPane;

    private Pane basePane;

    private HBox timeHB;

    private Group root ;
    private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    private final int HAUTEUR = (int)dimension.getHeight();
    private final int LARGEUR  = (int)dimension.getWidth();
    Text teamName;
    Text escapeGameName;
    Text playerName;
    Label remindedTime;
    Text descriptionEnigma;
    ComboBox listPlayer;
    private TextArea answer;
    private Button btnAnswer;
    private Label choicePlayer;

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
        progressBar.layoutXProperty().bind(basePane.widthProperty().subtract(progressBar.widthProperty()).divide(2));
        progressBar.setLayoutY(HAUTEUR*0.15);

        basePane.getChildren().addAll(progressBar);

        //definition de la liste des joueurs
        choicePlayer =new Label("choix du joueur");
        choicePlayer.setLayoutX(0);
        choicePlayer.setLayoutY(HAUTEUR*0.25);

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Option 1",
                        "Option 2",
                        "Option 3"
                );
        listPlayer = new ComboBox(options);
        listPlayer.setLayoutX(0);
        listPlayer.setLayoutY(4*HAUTEUR/15);

        basePane.getChildren().addAll(choicePlayer, listPlayer);
        //definition temps restant + graph minuteur
        timeHB = new HBox(2);
        remindedTime = new Label("1:00:00");

        timeIndicator = new ProgressIndicator();
        timeIndicator.setProgress(0.3);

        timeHB.getChildren().addAll(remindedTime, timeIndicator);

        timeHB.layoutXProperty().bind(basePane.widthProperty().subtract(timeHB.widthProperty()).divide(2));
        timeHB.setLayoutY(HAUTEUR*0.08);
        basePane.getChildren().addAll(timeHB);

        //Definition du chaps de description de l'énigme
        descriptionEnigma = new Text();
        descriptionEnigma.setText("Ceci est la description de l'énigme actuellement proposée à l'étudiant");
        descriptionEnigma.setLayoutX(0);
        descriptionEnigma.setLayoutY(6*HAUTEUR/15);

        basePane.getChildren().addAll(descriptionEnigma);

        //Definition champ réponse + btn envoie
        bottomPane = new StackPane();
        bottomPane.setPrefSize(4*LARGEUR/5,5* HAUTEUR/15);
        answer = new TextArea("Entrez un indice/réponse");
        btnAnswer = new Button("Envoyer");
        btnAnswer.setLayoutX(4*LARGEUR/5);
        btnAnswer.setLayoutY(10*HAUTEUR/15);
        btnAnswer.setPrefSize(LARGEUR/5, HAUTEUR/3);

        bottomPane.getChildren().addAll(answer);

        bottomPane.setLayoutY(10*HAUTEUR/15);
        bottomPane.setLayoutX(0);
        basePane.getChildren().addAll(bottomPane);
        basePane.getChildren().addAll(btnAnswer);

        //Génération de la scene
        root.getChildren().add(basePane);
        this.setTitle("FENETRE DU MJ");
        this.setScene(scene);
        this.show();
        //control = new MJController(scene,btn);
        return control;
    }

}