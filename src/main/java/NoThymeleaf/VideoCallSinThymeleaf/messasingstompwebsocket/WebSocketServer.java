package NoThymeleaf.VideoCallSinThymeleaf.messasingstompwebsocket;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Definimos el endPoint para el servidor
 */
@ServerEndpoint(value = "/VideoCall/gs-guide-websocket/websocket")
@Component
@Scope("prototype")
public class WebSocketServer {

    /**
     * Static variable that records the current number of online connections.It should be designed to be thread safe.
     */
    private static int onlineCount = 0;
    /**
     * concurrent The thread-safe Set of the package that holds the MyWebSocket object for each client.
     */
    private static ConcurrentHashMap<String, Session> webSocketMap = new ConcurrentHashMap<>();
    /**
     * A connection session with a client through which data is sent to the client
     */
    private Session session;
    /**
     * Receive userId
     */
    private String userId = "";

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {

        this.session = session;
        this.userId = userId;

        /**
         * Connection opened: add session to socket-map
         */
        webSocketMap.put(userId, session);
        System.out.println(userId + " - Connection established successfully...");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            Enumeration<String> keys =webSocketMap.keys();
            System.out.println("******** "+keys);

            while(keys.hasMoreElements()) {
                String key = keys.nextElement();
                //Determine if the user is still online
                if (webSocketMap.get(key) == null){
                    webSocketMap.remove(key);
                    System.err.println(key + " : null");
                    continue;
                }
                Session sessionValue = webSocketMap.get(key);
                //Remove Forwarding to Yourself
                if (key.equals(this.userId)){
                    System.err.println("my id " + key);
                    continue;
                }
                //Determine if session is open
                if (sessionValue.isOpen()){
                    sessionValue.getBasicRemote().sendText(message);
                }else {
                    System.err.println(key + ": not open");
                    sessionValue.close();
                    webSocketMap.remove(key);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("Connection exception...");
        error.printStackTrace();
    }

    @OnClose
    public void onClose() {
        System.out.println("Connection closed");
    }

    /**
     * Obtenemos las cuentas online
     * @return
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * Agregamos las cuentas al ponerse online
     */
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    /**
     * Desconectamos las cuentas cerradas
     */
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}
