package fr.unice.polytech.pel.polyescape.model.communication;

import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.DeploymentException;
import java.net.URI;
import java.net.URISyntaxException;

public class DataGetter {

    public DataGetter() {
    }

    public String getEnigmes(){
        String response = "";
        String request = "{\"request\":\"GET_ENIGME\"}";
        Client myClientEndPoint = new Client(request);
        ClientManager client = ClientManager.createClient();
        try {
            client.connectToServer(myClientEndPoint, new URI("ws://localhost:15555/websockets/gameserver"));
        }catch (DeploymentException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}

