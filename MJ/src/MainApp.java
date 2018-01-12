import model.ScreenMJ;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        ScreenMJ screenMJ = new ScreenMJ();
        screenMJ.init();
    }
}
