package NoThymeleaf.VideoCallSinThymeleaf.messasingstompwebsocket;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

@Controller
public class GreetingController {

    private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);

    /**
     * Controller del chat, a el se le apunta a traves de la conexion SockJs, apuntando al messageMapping,
     * el recibe el mensaje y lo retorna para mostrarlo en la web
     * @param message
     * @return
     * @throws Exception
     */
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    @JsonCreator
    public Greeting prueba(HelloMessage message) throws Exception {

        /*String userId = "";
        final String rangeRamdomCode = "0123456789";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(10);

        userId = sb.append(rangeRamdomCode.charAt(rnd.nextInt(rangeRamdomCode.length()))).toString();

        webSocketMap.put(userId, session);*/

        /**
         * Devolvemos un new Greeting, mandandolo al html con el texto que recibimos de del html
         */
        logger.info("User send message...");
        return new Greeting(HtmlUtils.htmlEscape(message.getText()));

    }

    @PostMapping("/sessionId/websocket/{sessionId}")
    private void sessionId (@PathVariable String sessionId) {
        logger.info(sessionId);
    }

}
