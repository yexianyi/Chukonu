package net.yxy.chukonu.spring.boot.websocket;

import java.io.IOException;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class MessageWebSocketHandler implements WebSocketHandler {
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		System.out.println("[Server]:"+session.getId()+ "|" + "afterConnectionEstablished()");
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage message) {
		try {
			System.out.println("[Server]:"+session.getId()+ "|" + "handleMessage()");
			session.sendMessage(new TextMessage("comfirmed message:"+message.getPayload().toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) {
		System.out.println("[Server]:"+session.getId()+ "|" + "handleTransportError()");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
		System.out.println("[Server]:"+session.getId()+ "|" + "afterConnectionClosed()");
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
}