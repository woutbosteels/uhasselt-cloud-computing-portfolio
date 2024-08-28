package eu.bosteels.wout.kwikbeheer.websockets;

import eu.bosteels.wout.kwikbeheer.model.Room;
import eu.bosteels.wout.kwikbeheer.service.RoomService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final SocketHandler socketHandler;
    private final TempSocketHandler tempSocketHandler;


    public WebSocketConfig(SocketHandler socketHandler, TempSocketHandler tempSocketHandler) {
        this.socketHandler = socketHandler;
        this.tempSocketHandler = tempSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        System.out.println("registerWebSocketHandlers");
        registry.addHandler(socketHandler, "/chatroom");
        registry.addHandler(tempSocketHandler, "/temp");
    }
}