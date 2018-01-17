package fr.unice.polytech.pel.polyescape;

import javafx.application.Application;
import javafx.stage.Stage;
import fr.unice.polytech.pel.polyescape.model.MasterGame;

public class MainApp extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        MasterGame masterGame = new MasterGame();
    }
}
