import javafx.application.Application;
import javafx.stage.Stage;
import model.MasterGame;
import model.communication.Client;
import model.communication.DataGetter;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.websockets.WebSocket;

import javax.websocket.DeploymentException;
import java.net.URI;
import java.net.URISyntaxException;

public class MainApp extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        MasterGame masterGame = new MasterGame();
    }
}
