package eu.bosteels.wout.kwikbeheer.websockets;

import eu.bosteels.wout.kwikbeheer.model.Room;
import eu.bosteels.wout.kwikbeheer.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;
import java.util.*;

@Component
public class TempSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(TempSocketHandler.class);

    private final Map<Long, List<WebSocketSession>> sessionsMap = new HashMap<>();

    private final SpringTemplateEngine templateEngine;
    private final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;
    private final RoomService roomService;
    private final ThreadPoolTaskExecutorBuilder threadPoolTaskExecutorBuilder;

    public TempSocketHandler(SpringTemplateEngine templateEngine, MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter, RoomService roomService, ThreadPoolTaskExecutorBuilder threadPoolTaskExecutorBuilder) {
        this.templateEngine = templateEngine;
        this.mappingJackson2HttpMessageConverter = mappingJackson2HttpMessageConverter;
        this.roomService = roomService;
        this.threadPoolTaskExecutorBuilder = threadPoolTaskExecutorBuilder;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        logger.info("session opened: {}", session);
        MultiValueMap<String, String> parameters =
                UriComponentsBuilder.fromUriString(session.getUri().toString()).build().getQueryParams();
        Long roomId = Long.valueOf(parameters.get("room_id").getFirst());
        List<WebSocketSession> sessions;
        if (sessionsMap.containsKey(roomId)) {
            sessions = sessionsMap.get(roomId);
        } else {
            sessions = new ArrayList<>();
        }
        sessions.add(session);
        sessionsMap.put(roomId, sessions);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus status) throws Exception {
        logger.info("session closed: {}", session);
        logger.debug(Objects.requireNonNull(session.getUri()).toString());
        MultiValueMap<String, String> parameters =
                UriComponentsBuilder.fromUriString(String.valueOf(session.getUri())).build().getQueryParams();

        logger.debug(parameters.toString());
        logger.debug(sessionsMap.toString());

        Long roomId = Long.valueOf(parameters.get("room_id").getFirst());
        List<WebSocketSession> sessions = sessionsMap.get(roomId);
        sessions.remove(session);
    }

    public void tempToRoom(String ts, float celsius, String building, String room) {
        logger.info("tempToRoom: {},{},{},{}", ts, celsius, building, room);
        Optional<Room> roomOptional = roomService.getRoomIfExists(building, room);
        if (roomOptional.isPresent()) {
            List<WebSocketSession> webSocketSessions = sessionsMap.get(roomOptional.get().getId());
            if (webSocketSessions != null && !webSocketSessions.isEmpty()) {
                sendMessageToSessions(tempReadingHTML(room, building, ts, celsius), webSocketSessions);
            } else {
                logger.info("No WS sessions waiting for reading");
            }
        } else {
            logger.info("No room with building {} and name {}", building, room);
        }
    }

    private void sendMessageToSessions(String message, List<WebSocketSession> webSocketSessions) {
        for (WebSocketSession webSocketSession : webSocketSessions) {
            logger.info(webSocketSession.toString());
            try {
                if (webSocketSession.isOpen()) {
                    webSocketSession.sendMessage(new TextMessage(message));
                }
            } catch (IOException e) {
                logger.debug("Unable to send message to {}", webSocketSession);
            }
        }

    }

    private String tempReadingHTML(String room, String building, String ts, float celsius)
    {
        Map<String, Object> map = new HashMap<>();
//        map.put("message", message);
        map.put("ts", ts);
        map.put("celsius", celsius);
        map.put("building", building);
        map.put("room", room);
        Context context = new Context(null, map);
        return templateEngine.process("temperature", Set.of("temperature-reading"), context);
    }
}