package eu.bosteels.wout.kwikbeheer.websockets;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(SocketHandler.class);

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    private final ObjectMapper objectMapper;
    private final SpringTemplateEngine templateEngine;

    public SocketHandler(ObjectMapper objectMapper, SpringTemplateEngine templateEngine) {
        this.objectMapper = objectMapper;
        this.templateEngine = templateEngine;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws IOException {
        Map<String, Object> value = objectMapper.readValue(message
                .getPayload(), new TypeReference<>() {
        });
        String userMessage = (String) value.get("chatMessage");
        logger.info("userMessage = {}", userMessage);
        sendMessageToWebSocketsessions(session, userMessage);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        logger.info("session opened: {}", session);
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus status) throws Exception {
        logger.info("session closed: {}", session);
        sessions.remove(session);
    }

    public void sendMessageToChatRoom(String message) {
        String html = incomingMessageHtml(message);
        for (WebSocketSession webSocketSession : sessions) {
            try {
                if (webSocketSession.isOpen()) {
                    webSocketSession.sendMessage(new TextMessage(html));
                }
            } catch (IOException e) {
                logger.debug("Unable to send message to {}", webSocketSession);
            }
        }
    }

        private void sendMessageToWebSocketsessions(WebSocketSession
                                                        currentSession, String message) {
        for (WebSocketSession webSocketSession : sessions) {
            try {
                if (webSocketSession.isOpen()) {

                    String html;
                    if (webSocketSession.equals(currentSession)) {
                        html = userMessageHtml(message);
                    } else {
                        html = incomingMessageHtml(message);
                    }
                    webSocketSession.sendMessage(new TextMessage(html));

                }
            } catch (IOException e) {
                logger.debug("Unable to send message to {}",
                        webSocketSession);
            }
        }
    }

    private String userMessageHtml(String message) {
        Context context = new Context(null, Map.of("message", message));
        return templateEngine.process("chat", Set.of("user-message"), context);
    }

    private String incomingMessageHtml(String message) {
        Context context = new Context(null, Map.of("message", message));
        return templateEngine.process("chat", Set.of("incoming-message"), context);
    }
}