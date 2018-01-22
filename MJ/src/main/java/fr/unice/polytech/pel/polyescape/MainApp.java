package fr.unice.polytech.pel.polyescape;

import fr.unice.polytech.pel.polyescape.model.MasterGame;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        MasterGame masterGame = new MasterGame();
    }
}
