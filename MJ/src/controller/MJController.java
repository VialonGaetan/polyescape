package controller;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


public class MJController {

    private Button btn;
    private Scene scene;

    public MJController(Scene scene, Button btn){
        this.btn = btn;
        this.scene = scene;
        Group root = (Group)scene.getRoot();
        Pane basePane =(Pane) root.getChildren().get(0);
        basePane.getChildren().add(btn);
    }
}
