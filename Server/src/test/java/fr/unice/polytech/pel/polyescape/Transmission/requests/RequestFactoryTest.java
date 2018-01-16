package fr.unice.polytech.pel.polyescape.Transmission.requests;

import fr.unice.polytech.pel.polyescape.Transmission.JsonArguments;
import fr.unice.polytech.pel.polyescape.Transmission.TypeRequest;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Gaetan Vialon
 * Created the 16/01/2018.
 */
public class RequestFactoryTest {

    private String createPartieRequest;
    private String checkReponseRequest;
    private String createEscapeRequest;
    private String getEscapeGameRequest;
    private String getEnigmeRequest;
    private String getPartieEnCoursRequest;
    private RequestFactory requestFactory;

    @Before
    public void setUp()
    {

        requestFactory = new RequestFactory();
        //createEscapeRequest = new JSONObject().put(JsonArguments.REQUEST.toString(),TypeRequest.CREATE_PARTIE).put(JsonArguments.ESCAPEGAME.toString(),"lol");
        getEnigmeRequest = new JSONObject().put(JsonArguments.REQUEST.toString(),TypeRequest.GET_ENIGME).toString();
        getEscapeGameRequest = new JSONObject().put(JsonArguments.REQUEST.toString(),TypeRequest.GET_ESCAPE).toString();
        checkReponseRequest = new JSONObject().put(JsonArguments.REQUEST.toString(),TypeRequest.RESPONSE).toString();
        getPartieEnCoursRequest = new JSONObject().put(JsonArguments.REQUEST.toString(),TypeRequest.GET_PARTIES).toString();

    }

    @Test
    public void testRequest(){
        //assertTrue(requestFactory.createTypeRequest(createEscapeRequest,null) instanceof CreatePartieRequest );
        assertTrue(requestFactory.createTypeRequest(getEnigmeRequest,null) instanceof EnigmeDispoRequest );
        assertTrue(requestFactory.createTypeRequest(getEscapeGameRequest,null) instanceof EscapeGameDispoListRequest );
        assertTrue(requestFactory.createTypeRequest(getPartieEnCoursRequest,null) instanceof PartieEnCoursListRequest );



    }

}