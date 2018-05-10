package net.yxy.chukonu.spring.boot.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class MessageWebSocketHandler implements WebSocketHandler {
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		System.out.println(session.getId()+ "|" + "afterConnectionEstablished()");
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage message) {
		System.out.println(session.getId()+ "|" + "handleMessage()");
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) {
		System.out.println(session.getId()+ "|" + "handleTransportError()");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
		System.out.println(session.getId()+ "|" + "afterConnectionClosed()");
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
}