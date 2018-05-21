package net.yxy.chukonu.websocket.client;


import java.io.IOException;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;



@ClientEndpoint
public class WebSocketClient {

	private Session session;
	
	@OnOpen
	public void open(Session session){
		this.session = session;
		System.out.println("Client: open()");
	}
	
	@OnMessage
	public void onMessage(String message){
		System.out.println("Client received msg:"+message);
	}
	
	@OnClose
	public void onClose(){
		System.out.println("Client: onClose()");
	}
	

    @OnError
    public void onError(Session session, Throwable t) {
    	System.out.println("Client: onError()");
        t.printStackTrace();
    }
    
	public void send(Object message){
		this.session.getAsyncRemote().sendObject(message);
	}
	
	public void close() throws IOException{
		if(this.session.isOpen()){
			this.session.close();
		}
	}
}

