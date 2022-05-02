package NoThymeleaf.VideoCallSinThymeleaf.messasingstompwebsocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Controller
@Configuration
@EnableWebSocket
public class WebSocketChatConfiguration implements WebSocketConfigurer {

    /**
     * Le asignamos los handlers a los url especificos y tambien permitimos cualquier origen, sea http o https
     * @param registry
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(signalHandler(), "/VideoCall/gs-guide-websocket/websocket" ,
                "/gs-guide-websocket/websocket", "/session/websocket")
                .setAllowedOriginPatterns("*");

    }

    /**
     * Hacemos new de la clase handler
     * @return
     */
    @Bean
    public WebSocketHandler signalHandler() {
        return new ChatWebSocketHandler();
    }

}
