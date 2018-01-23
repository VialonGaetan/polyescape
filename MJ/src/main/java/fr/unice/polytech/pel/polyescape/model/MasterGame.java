package fr.unice.polytech.pel.polyescape.model;

import fr.unice.polytech.pel.polyescape.controller.MJController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.websocket.DeploymentException;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class MasterGame extends Stage{

    private ImageView logo;
    private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    private final int HAUTEUR = (int)dimension.getHeight();
    private final int LARGEUR  = (int)dimension.getWidth();

    private StackPane bottomPane;
    private StackPane topPane;
    private Pane basePane;
    private HBox timeHB;

    private ProgressIndicator teamProgress;
    private ProgressIndicator timeIndicator;

    private Text descriptionEnigma;
    private Text teamName;
    private Text escapeGameName;
    private Text playerName;
    private TextArea answer;


    private Label remindedTime;
    private Label choicePlayer;
    private Label progressLabel;

    private ComboBox listPlayer;
    private Button btnAnswer;

    private Group root;
    private MJController control;

    public MasterGame() throws URISyntaxException, DeploymentException, InterruptedException, IOException {
        init();
    }

    public MJController init() throws URISyntaxException, DeploymentException, InterruptedException, IOException {
        //Definition de base
        basePane = new Pane();
        root = new Group();
        Scene scene = new Scene(root,LARGEUR, HAUTEUR);

        //Definition partie haute
        topPane = new StackPane();
        topPane.setPrefSize(LARGEUR,HAUTEUR/15);
        escapeGameName = new Text("Escape game A");
        escapeGameName.setId("escapeGameName");
        teamName=new Text("Equipe A");
        teamName.setId("teamName");
        playerName = new Text("Nom du joueur");
        playerName.setId("playerName");

        topPane.getChildren().addAll(escapeGameName,teamName,playerName);

        topPane.setAlignment(escapeGameName, Pos.TOP_LEFT);
        topPane.setAlignment(teamName, Pos.TOP_CENTER);
        topPane.setAlignment(playerName,Pos.TOP_RIGHT);
        basePane.getChildren().addAll(topPane);

        //definition de la teamProgress + progressLabel
        teamProgress = new ProgressIndicator();
        teamProgress.setId("teamProgress");
        teamProgress.setProgress(0.4);
        System.out.println(teamProgress.getProgress());
        teamProgress.layoutXProperty().bind(basePane.widthProperty().subtract(teamProgress.widthProperty()).divide(2));
        teamProgress.setLayoutY(HAUTEUR*0.10);
        progressLabel = new Label("50%");
        progressLabel.setId("progressLabel");
        progressLabel.layoutXProperty().bind(basePane.widthProperty().subtract(progressLabel.widthProperty()).divide(2));
        progressLabel.setLayoutY(teamProgress.getLayoutY()+HAUTEUR/50);


        basePane.getChildren().addAll(teamProgress, progressLabel);

        //definition de la liste des joueurs
        choicePlayer =new Label("choix du joueur :");
        choicePlayer.setLayoutX(0);
        choicePlayer.setLayoutY(HAUTEUR*0.25);
        choicePlayer.setId("choicePlayer");

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Option 1",
                        "Option 2",
                        "Option 3"
                );

        listPlayer = new ComboBox(options);
        listPlayer.setId("listPlayer");
        listPlayer.setLayoutX(0);
        listPlayer.setLayoutY(HAUTEUR*0.3);


        basePane.getChildren().addAll(choicePlayer, listPlayer);

        //definition temps restant + graph minuteur
        timeHB = new HBox(2);
        remindedTime = new Label("1:00:00");
        remindedTime.setId("remindedTime");

        timeIndicator = new ProgressIndicator();
        timeIndicator.setProgress(1);
        timeIndicator.setId("timeIndicator");

        timeHB.getChildren().addAll(remindedTime, timeIndicator);

        timeHB.layoutXProperty().bind(basePane.widthProperty().subtract(timeHB.widthProperty()).divide(1.1));
        timeHB.setLayoutY(6*HAUTEUR/15);
        timeHB.setSpacing(LARGEUR/40);

        basePane.getChildren().addAll(timeHB);

        //Definition du chaps de description de l'énigme
        descriptionEnigma = new Text();
        descriptionEnigma.setText("Selectionnez un joueur");
        descriptionEnigma.setLayoutX(0);
        descriptionEnigma.setLayoutY(6*HAUTEUR/15);
        descriptionEnigma.setWrappingWidth(2*LARGEUR/3);
        descriptionEnigma.setId("descriptionEnigma");

        basePane.getChildren().addAll(descriptionEnigma);

        //Definition champ réponse + btn envoie
        bottomPane = new StackPane();
        bottomPane.setPrefSize(4*LARGEUR/5,5* HAUTEUR/15);
        answer = new TextArea();
        answer.setId("answer");
        answer.setStyle("-fx-background-color:#488aff;");
        answer.setWrapText(true);
        btnAnswer = new Button("Envoyer");
        btnAnswer.setId("btnAnswer");
        btnAnswer.setLayoutX(4*LARGEUR/5);
        btnAnswer.setLayoutY(10*HAUTEUR/15);
        btnAnswer.setPrefSize(LARGEUR/5, HAUTEUR/3);

        bottomPane.getChildren().addAll(answer);
        bottomPane.setId("bottomPane");

        bottomPane.setLayoutY(10*HAUTEUR/15);
        bottomPane.setLayoutX(0);
        basePane.getChildren().addAll(bottomPane);
        basePane.getChildren().addAll(btnAnswer);

        //Definition du logo au centre de la page
        logo = new ImageView("images/logoPoly.png");
        logo.setLayoutX(LARGEUR/2 - logo.getImage().getWidth()/2);
        logo.setLayoutY(HAUTEUR/2 - logo.getImage().getHeight()/2);
        logo.setId("logo");

        logo.setOpacity(0.10);

        basePane.getChildren().addAll(logo);
        basePane.setId("basePane");

        //Génération de la scene
        root.getChildren().add(basePane);
        this.setTitle("FENETRE DU MJ");
        this.getIcons().add(new Image(getClass().getClassLoader().getResource("images/loupe.jpg").toString()));

        this.setScene(scene);
        this.getScene().getStylesheets().addAll(getClass().getClassLoader().getResource("styles/style.css").toExternalForm());

        this.show();
        control = new MJController(scene, topPane, teamProgress,choicePlayer, listPlayer,timeHB,bottomPane, btnAnswer, progressLabel, timeIndicator, descriptionEnigma);
        return control;
    }

}