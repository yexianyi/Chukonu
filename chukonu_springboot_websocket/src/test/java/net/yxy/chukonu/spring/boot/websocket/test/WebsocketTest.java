package net.yxy.chukonu.spring.boot.websocket.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
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

import net.yxy.chukonu.spring.boot.websocket.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WebsocketTest {
	private final Logger logger = LoggerFactory.getLogger(WebsocketTest.class);
	private final WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
	final CountDownLatch latch = new CountDownLatch(1);
	final AtomicReference failure = new AtomicReference<>();
	@LocalServerPort
	private int port;
	private SockJsClient sockJsClient;

	@Before
	public void setup() {
		List transports = new ArrayList<>();
		transports.add(new WebSocketTransport(new StandardWebSocketClient()));
		transports.add(new RestTemplateXhrTransport());
		this.sockJsClient = new SockJsClient(transports);
		this.sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());
	}

	@Test
	public void getGreeting() throws Exception {
		this.sockJsClient.doHandshake(new TestWebSocketHandler(),
				"ws://localhost:" + String.valueOf(port) + "websocket/sockjs/message?siteId=webtrn&userId=lucy");
		if (latch.await(60, TimeUnit.SECONDS)) {
			if (failure.get() != null) {
				throw new AssertionError(failure.get());
			}
		} else {
			//Greeting not received;
		}
	}

	private class TestWebSocketHandler implements WebSocketHandler {

		@Override
		public void afterConnectionEstablished(WebSocketSession session) throws Exception {
			logger.info("client connection established");
			session.sendMessage(new TextMessage("hello websocket server!"));
		}

		@Override
		public void handleMessage(WebSocketSession session, WebSocketMessage message) throws Exception {
			String payload = (String) message.getPayload();
			logger.info("client handle message: " + payload);
			if (payload.equals("hello websocket client! webtrn lucy")) {
				latch.countDown();
			}
			if (payload.equals("web socket notify")) {
				latch.countDown();
			}
		}

		@Override
		public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
			logger.info("client transport error");
		}

		@Override
		public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
			logger.info("client connection closed");
		}

		@Override
		public boolean supportsPartialMessages() {
			return false;
		}
	}
}