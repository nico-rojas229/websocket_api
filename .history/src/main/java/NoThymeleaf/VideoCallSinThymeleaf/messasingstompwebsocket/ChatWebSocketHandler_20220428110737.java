package NoThymeleaf.VideoCallSinThymeleaf.messasingstompwebsocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

//TODO: Edi 10.03.2022
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private GreetingController greetingController;

    /**
     * Logger para los logs
     */
    private static final Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);

    /**
     * Lista de listas, en esta lista se guardan las conexiones webSocket peer to peer
     */
    private List<List> rooms = new CopyOnWriteArrayList<>();
    /**
     * En esta lista es donde se realiza la conexion de los pares
     */
    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    /**
     * Contabilidad de los usuarios
     */
    private Integer users = 0;
    /**
     * Contabilidad de los rooms
     */
    private Integer numberRoom = 0;

    /**
     * Este metodo se lleva acabo cuando se conecta un usuario a la pagina asignandole una session
     * @param session
     */
    @Override
    public void afterConnectionEstablished (WebSocketSession session) {

        /**
         * Sumamos los usuarios que vayan ingresando
         */
        users = users + 1;

        /**
         * Este condicional es para cuando la conexion Peer to peer se encuentre full (2 usuarios),
         * De ser asi, se mueve esa conexion a la lista de listas, se limpia la lista de webSocket,
         * volvemos la contabilidad de usuarios a 0 y agregamos el nuevo
         */
        if (users > 2) {
            rooms.add(sessions);
            sessions = new CopyOnWriteArrayList<>();
            users = 0;
            users = users + 1;
        }

        if (users == 1) {
            numberRoom = numberRoom + 1;
            logger.info("Room "+ numberRoom);
        }


        /**
         * Se agrega la session a la lista de webSocket
         */
        sessions.add(session);
        logger.info("User connected! ID: "+session.getId());

    }

    /**
     * Este metodo se lleva acabo cuando se cierra una session de webSocket y remueve esa session
     * de la lista donde lo encuentre
     * @param session
     * @param status
     */
    @Override
    public void afterConnectionClosed (WebSocketSession session, CloseStatus status) {

        /**
         * Removemos la session que se cerro
         */
        sessions.remove(session);
        users = users - 1;
        logger.info("User disconnected! ID: "+session.getId());

    }

    @Override
    public void handleTextMessage (WebSocketSession session, TextMessage message) {

        /**
         * Solo se puede hacer conexion cuando hay 2 usuarios conectados
         */
        if (users == 2) {

            try {

                /**
                 * For para recorrer la lista de sessions
                 */
                for (WebSocketSession webSocketSession : sessions) {

                    /**
                     * Si la webSocketSession es distinta a la session se manda un message que es lo que realiza
                     * la conexion entre ellos
                     */
                    if (!session.getId().equals(webSocketSession.getId())) {
                        webSocketSession.sendMessage(message);      // Send message
                    }

                }

            } catch (Exception e) {
                logger.error(e.getMessage());       // Error message
            }

        }

    }

}
