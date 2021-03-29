package org.nelis.securechat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.undertow.Undertow;
import io.undertow.util.Headers;
import org.apache.catalina.LifecycleException;
import org.nelis.securechat.service.blocking.ChatRoomManager;
import org.nelis.securechat.service.blocking.dao.DaoManager;
import org.nelis.securechat.service.blocking.servlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws LifecycleException {
        logger.info("Starting up data access..");
        DaoManager daoManager = new DaoManager();

        logger.info("Creating ChatServlet..");
        ChatServlet chatServlet = createChatServlet(daoManager);

        logger.info("Starting Tomcat..");
        TxFilter filter = createChatFilter(daoManager);
        TomcatHelper.startTomcat(chatServlet, filter,8082);

        // curl --data-ascii "{\"name\":\"test\"}" http://localhost:8082/createroom
    }

    public static TxFilter createChatFilter(DaoManager daoManager) {
        TxFilter filter = new TxFilter(daoManager.getSessionFactory());
        return filter;
    }

    public static ChatServlet createChatServlet(DaoManager daoManager) {
        ChatRoomManager chatRoomManager = new ChatRoomManager(daoManager.getChatRoomDao(), daoManager.getUserDao());

        ObjectMapper objectMapper = new ObjectMapper();
        CreateRoom createRoom = new CreateRoom(chatRoomManager,objectMapper);
        CreateUser createUser = new CreateUser(chatRoomManager,objectMapper);
        SendMessage sendMessage = new SendMessage(chatRoomManager,objectMapper);
        ShowMessages showMessages = new ShowMessages(daoManager.getChatRoomDao(), objectMapper);

        List<ChatServletCommand> commands = new ArrayList<>();
        commands.add(createRoom);
        commands.add(createUser);
        commands.add(sendMessage);
        commands.add(showMessages);

        ChatServlet chatServlet = new ChatServlet(commands);
        return chatServlet;
    }

    private static void startUndertow() {
        Undertow server = Undertow.builder()
                .addHttpListener(8080,"localhost")
                .setHandler(exchange -> {
                                exchange.getResponseHeaders()
                                .put(Headers.CONTENT_TYPE, "text/plain");
                                exchange.getResponseSender().send("Hello Baeldung");
                           })
                .build();
        server.start();
    }


}
