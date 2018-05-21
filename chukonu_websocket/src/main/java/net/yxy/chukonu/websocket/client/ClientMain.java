package net.yxy.chukonu.websocket.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;

import net.yxy.chukonu.embedded.tomcat.util.NetUtil;

public class ClientMain {

	public static void main(String[] args) 
            throws DeploymentException, IOException, URISyntaxException, InterruptedException {
        // Auto-generated method stub
        WebSocketContainer conmtainer = ContainerProvider.getWebSocketContainer();
        WebSocketClient client = new WebSocketClient();
//        conmtainer.connectToServer(client, new URI("ws://localhost:8080/chukonu_websocket/websocket/test1"));
        conmtainer.connectToServer(client, new URI("ws://localhost:8080/websocket/sockjs/message?siteId=webtrn&userId=lucy"));

        int turn = 0;
        while(true){
            client.send("ping test " + turn++);
            Thread.sleep(3000);
        }
    }



}
