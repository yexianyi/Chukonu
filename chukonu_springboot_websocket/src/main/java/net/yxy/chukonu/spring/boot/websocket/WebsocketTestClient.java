package net.yxy.chukonu.spring.boot.websocket;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;


public class WebsocketTestClient {
	private final static Logger logger = LoggerFactory.getLogger(WebsocketTestClient.class);
	private final WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

	
	public static void main(String[] args) {
		List transports = new ArrayList<>();
		transports.add(new WebSocketTransport(new StandardWebSocketClient()));
		transports.add(new RestTemplateXhrTransport());
		SockJsClient sockJsClient = new SockJsClient(transports);
		sockJsClient = new SockJsClient(transports);
		sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());
		
		ListenableFuture<WebSocketSession> session = sockJsClient.doHandshake(new TestWebSocketHandler(), 
				"ws://localhost:8080/websocket/sockjs/message?siteId=webtrn&userId=lucy");

		while(true) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
	}

	private static class TestWebSocketHandler implements WebSocketHandler {

		@Override
		public void afterConnectionEstablished(WebSocketSession session) throws Exception {
			logger.info("client connection established");
			session.sendMessage(new TextMessage("hello websocket server!"));
		}

		@Override
		public void handleMessage(WebSocketSession session, WebSocketMessage message) throws Exception {
			logger.info("[client] message:" + message.getPayload().toString());
			WebSocketMessage<String> reply = new TextMessage("client confirmed message.") ;
			Thread.sleep(5000);
			session.sendMessage(reply);
		}

		@Override
		public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
			logger.info("[client] error:"+exception.getMessage());
		}

		@Override
		public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
			logger.info("[client] connection closed");
		}

		@Override
		public boolean supportsPartialMessages() {
			return false;
		}
	}
}