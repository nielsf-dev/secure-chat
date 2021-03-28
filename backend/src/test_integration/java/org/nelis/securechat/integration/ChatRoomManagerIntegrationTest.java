package org.nelis.securechat.integration;

import javassist.NotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.nelis.securechat.domain.ChatMessage;
import org.nelis.securechat.domain.ChatRoom;
import org.nelis.securechat.domain.User;
import org.nelis.securechat.service.blocking.dao.DaoManager;
import org.nelis.securechat.service.blocking.ChatRoomManager;
import org.nelis.securechat.service.blocking.dao.ChatRoomDao;
import org.nelis.securechat.service.blocking.dao.UserDao;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChatRoomManagerIntegrationTest {

    private static DaoManager daoManager;
    private static Session currentSession;
    private static Transaction tx;

    @BeforeAll
    static void beforeAll() {
        daoManager = new DaoManager();

        SessionFactory sessionFactory = daoManager.getSessionFactory();
        currentSession = sessionFactory.getCurrentSession();

        tx = currentSession.beginTransaction();
    }

    @AfterAll
    static void afterAll(){
        tx.commit();
    }

    @Test
    void testChatRoom() {
        ChatRoomDao chatRoomDao = daoManager.getChatRoomDao();
        UserDao userDoa = daoManager.getUserDao();

        ChatRoomManager chatRoomManager = new ChatRoomManager(chatRoomDao, userDoa);

        long chatRoomId = chatRoomManager.createChatRoom("testroom");
        long user1Id = chatRoomManager.createUser("user1");
        chatRoomManager.addUserToRoom(chatRoomId, user1Id);

        long user2Id = chatRoomManager.createUser("user2");
        chatRoomManager.addUserToRoom(chatRoomId, user2Id);

        boolean success = chatRoomManager.sendChatMessage(chatRoomId, user1Id, new ChatMessage("cool message"));
        assertTrue(success);

        success = chatRoomManager.sendChatMessage(chatRoomId, user2Id, new ChatMessage("haai"));
        assertTrue(success);

        success = chatRoomManager.sendChatMessage(chatRoomId, user1Id, new ChatMessage("doeei"));
        assertTrue(success);
    }
}
