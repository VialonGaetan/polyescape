import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;

public class ScreenMJ{

    private Pane basePane;
    private Group root ;
    private javafx.scene.text.Text text;
    private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    private final int HEIGHT = (int)dimension.getHeight();
    private final int WIDTH  = (int)dimension.getWidth();

    public ScreenMJ(){
        System.out.println("ok");
        text = new javafx.scene.text.Text();
        text.setText("okokokok");
        basePane = new Pane();
        root = new Group();
        basePane.setPrefSize(WIDTH,HEIGHT);
        Scene scene = new Scene(root,WIDTH, HEIGHT);
        /*this.setScene(scene);
        this.show();*/
    }

    public void print(){
        System.out.println("ok");
    }

}
