package net.yxy.chukonu.websocket.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;

public class Client {

	public static void main(String[] args) 
            throws DeploymentException, IOException, URISyntaxException, InterruptedException {
        // Auto-generated method stub
        WebSocketContainer conmtainer = ContainerProvider.getWebSocketContainer();
        WebSocketClient client = new WebSocketClient();
        conmtainer.connectToServer(client, new URI("ws://localhost:8080/chukonu_websocket/websocket/test1"));

        int turn = 0;
        while(turn++ < 10){
            client.send("send text: " + turn);
            Thread.sleep(1000);
        }
        client.close();
    }



}
