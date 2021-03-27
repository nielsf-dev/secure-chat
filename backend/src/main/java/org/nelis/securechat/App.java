package org.nelis.securechat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.undertow.Undertow;
import io.undertow.util.Headers;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Server;
import org.apache.catalina.startup.Tomcat;
import org.nelis.securechat.service.blocking.ChatRoomManager;
import org.nelis.securechat.service.blocking.dao.DaoManager;
import org.nelis.securechat.service.blocking.servlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws LifecycleException {
        logger.info("Creating ChatServlet..");
        ChatServlet chatServlet = createChatServlet();

        logger.info("Starting Tomcat..");
        TomcatHelper.startTomcat(chatServlet,8082);

        // curl --data-ascii "{\"name\":\"test\"}" http://localhost:8082/createroom
    }

    public static ChatServlet createChatServlet() {
        DaoManager daoManager = new DaoManager();
        ChatRoomManager chatRoomManager = new ChatRoomManager(daoManager.getChatRoomDao(), daoManager.getUserDao());

        ObjectMapper objectMapper = new ObjectMapper();
        CreateRoom createRoom = new CreateRoom(chatRoomManager,objectMapper,daoManager.getSessionFactory());
        CreateUser createUser = new CreateUser(chatRoomManager,objectMapper,daoManager.getSessionFactory());
        SendMessage sendMessage = new SendMessage(chatRoomManager,objectMapper,daoManager.getSessionFactory());
        ShowMessages showMessages = new ShowMessages(daoManager.getChatRoomDao(),objectMapper,daoManager.getSessionFactory());

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
