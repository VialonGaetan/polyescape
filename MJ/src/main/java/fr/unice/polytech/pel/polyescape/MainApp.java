package fr.unice.polytech.pel.polyescape;

import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import fr.unice.polytech.pel.polyescape.Transmission.TypeRequest;
import fr.unice.polytech.pel.polyescape.model.MasterGame;
import fr.unice.polytech.pel.polyescape.model.communication.ClientMJ;
import javafx.application.Application;
import javafx.stage.Stage;
import org.glassfish.tyrus.client.ClientManager;
import org.json.JSONObject;

import java.net.URI;

public class MainApp extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        MasterGame masterGame = new MasterGame();
//        JSONObject jsonObject = new JSONObject().put(JsonArguments.REQUEST.toString(), TypeRequest.GET_PARTIES);
//        String request = jsonObject.toString();
//        ClientMJ clientMJ = new ClientMJ(request);
//        ClientManager client = ClientManager.createClient();
//        client.connectToServer(clientMJ, new URI("ws://localhost:15555/websockets/gameserver"));
//        System.out.println(clientMJ.getAnswer());
    }
}
