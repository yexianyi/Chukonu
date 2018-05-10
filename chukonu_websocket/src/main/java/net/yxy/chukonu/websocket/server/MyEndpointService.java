package net.yxy.chukonu.websocket.server;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket/{username}")
public class MyEndpointService {

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException, EncodeException {
    	System.out.println(session.getId()+"|"+username);
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException, EncodeException {
    	System.out.println(session.getId()+"|"+"onMessage():"+message);
    	 try {
             session.getBasicRemote().sendText("Hello Client " + session.getId() + "!");
         } catch (IOException e) {
             e.printStackTrace();
         }

    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
    	System.out.println(session.getId()+"|"+"onClose()");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
    	System.out.println(session.getId()+"|"+"onError():"+throwable.getMessage());
    	
    }

}

