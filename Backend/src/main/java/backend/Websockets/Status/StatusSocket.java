package backend.Websockets.Status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import backend.Websockets.Chat.Message;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
@Controller      // this is needed for this to be an endpoint to springboot
@ServerEndpoint(value = "/status/{username}")  // this is Websocket url
public class StatusSocket {


    private static StatusRepository stsRepo;


    @Autowired
    public void setMessageRepository(StatusRepository repo) {
        stsRepo = repo;  // we are setting the static variable
    }

    // Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(StatusSocket.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username)
            throws IOException, JSONException {

        logger.info("Entered into Open");
        stsRepo.save(new Status(username, true, false));
        // store connecting user information
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);
        sendMessageToPArticularUser(username, getChatHistory());
        JSONObject returnStatus = new JSONObject(); //returns

        for(int i = stsRepo.findAll().size() -1; i > 0; i--){
            Long id = stsRepo.findAll().get(i).getId();
            String name = stsRepo.findAll().get(i).getUserName();
            boolean online = stsRepo.findAll().get(i).getOnline();
            boolean away = stsRepo.findAll().get(i).getAway();
            Status s = new Status(name, online, away);
            if(username.equals(name)){
                stsRepo.delete(stsRepo.findAll().get(i));
            }
            else{
                returnStatus.put(name, online);
            }
        }
        stsRepo.save(new Status(username, true, false));
        returnStatus.put(username, true);

        // broadcast that new user joined
        broadcast(returnStatus.toString());
    }





    @OnClose
    public void onClose(Session session) throws IOException, JSONException {
        logger.info("Entered into Close");

        // remove the user connection information

        String username = sessionUsernameMap.get(session);

        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);

        // broadcase that the user disconnected
        JSONObject returnStatus = new JSONObject();

        for(int i = stsRepo.findAll().size() -1; i > 0; i--){
            Long id = stsRepo.findAll().get(i).getId();
            String name = stsRepo.findAll().get(i).getUserName();
            boolean online = stsRepo.findAll().get(i).getOnline();
            boolean away = stsRepo.findAll().get(i).getAway();
            Status s = new Status(name, online, away);
            if(username.equals(name)){
                stsRepo.delete(stsRepo.findAll().get(i));
            }
            else{
                returnStatus.put(name, online);
            }
        }
        stsRepo.save(new Status(username, false, false));
        returnStatus.put(username, false);

        // broadcast that new user joined
        broadcast(returnStatus.toString());
    }


    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.info("Entered into Error");
        throwable.printStackTrace();
    }



    private void sendMessageToPArticularUser(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        }
        catch (IOException e) {
            logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }


    private void broadcast(String message) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            }
            catch (IOException e) {
                logger.info("Exception: " + e.getMessage().toString());
                e.printStackTrace();
            }

        });

    }


    // Gets the Chat history from the repository
    private String getChatHistory() {
        List<Status> st = stsRepo.findAll();

        // convert the list to a string
        StringBuilder sb = new StringBuilder();
        if(st != null && st.size() != 0) {
            for (Status status : st) {
                sb.append(status.getUserName() + ": " + status.getOnline() + "\n");
            }
        }
        return sb.toString();
    }




}