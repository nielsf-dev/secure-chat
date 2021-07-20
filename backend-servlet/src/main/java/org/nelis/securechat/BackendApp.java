package org.nelis.securechat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.undertow.Undertow;
import io.undertow.util.Headers;
import org.apache.catalina.LifecycleException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.nelis.securechat.domain.ChatRoom;
import org.nelis.securechat.domain.User;
import org.nelis.securechat.service.blocking.*;
import org.nelis.securechat.service.blocking.servlet.*;
import org.nelis.securechat.service.blocking.servlet.commands.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BackendApp {
    private static Logger logger = LoggerFactory.getLogger(BackendApp.class);

    public static void main(String[] args) throws LifecycleException {
        logger.info("Starting up data access..");
        SessionFactory sessionFactory = new SessionFactoryBuilder().build();
        DaoRegistry daoRegistry = new DaoRegistryImp(sessionFactory);

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        long id = 2;
        ChatRoom load = session.load(ChatRoom.class, id);
        ChatRoom chatRoom = session.find(ChatRoom.class, id);
        User user = session.find(User.class, id);


        logger.info("Creating ChatServlet..");
        ChatServlet chatServlet = createChatServlet(daoRegistry);

        logger.info("Starting Tomcat..");
        TxFilter filter = createChatFilter(sessionFactory);
        TomcatHelper.startTomcat(chatServlet, filter,8082);

       // startUndertow();

        // curl --data-ascii "{\"name\":\"test\"}" http://localhost:8082/createroom
    }

    public static TxFilter createChatFilter(SessionFactory sessionFactory) {
        TxFilter filter = new TxFilter(sessionFactory);
        return filter;
    }

    public static ChatServlet createChatServlet(DaoRegistry daoRegistry) {
        ChatRoomManager chatRoomManager = new ChatRoomManager(daoRegistry.getChatRoomDao(), daoRegistry.getUserDao());

        ObjectMapper objectMapper = new ObjectMapper();
        CreateRoomCommand createRoomCommand = new CreateRoomCommand(chatRoomManager,objectMapper);
        CreateUserCommand createUserCommand = new CreateUserCommand(chatRoomManager,objectMapper);
        SendMessageCommand sendMessageCommand = new SendMessageCommand(chatRoomManager,objectMapper);
        ShowMessagesCommand showMessages = new ShowMessagesCommand(daoRegistry.getChatRoomDao(), objectMapper);

        List<Command> commands = new ArrayList<>();
        commands.add(createRoomCommand);
        commands.add(createUserCommand);
        commands.add(sendMessageCommand);
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
                                exchange.getResponseSender().send("yolo");
                           })
                .build();
        server.start();
    }


}
