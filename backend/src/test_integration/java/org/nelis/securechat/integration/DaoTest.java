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

public class DaoTest {

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
    public void testUser(){
        UserDao userDao = daoManager.getUserDao();

        User newUser = new User("Het werkt jongee");
        userDao.save(newUser);

        List<User> all = userDao.findAll();
        all.stream().forEach(user -> System.out.println(user.getName()));

        User user = userDao.find(newUser.getId());
        assertNotNull(user);
    }

    @Test
    void relationTest() throws NotFoundException {
        ChatRoomDao chatRoomDao = daoManager.getChatRoomDao();

        ChatRoom chatRoom = chatRoomDao.find(3);
        User firstUser = chatRoom.getUsers().stream().findFirst().get();
        chatRoom.sendMessage(firstUser.getId(),new ChatMessage("yolings"));
    }

    @Test
    void testChatRoom() {
        ChatRoomDao chatRoomDao = daoManager.getChatRoomDao();
        UserDao userDoa = daoManager.getUserDao();

        List<User> all = userDoa.findAll();
        ChatRoom chatRoom = new ChatRoom("test");
        User user1 = all.get(0);
        chatRoom.insertUser(user1);
        User user2 = all.get(1);
        chatRoom.insertUser(user2);
        chatRoomDao.save(chatRoom);

        ChatRoomManager chatRoomManager = new ChatRoomManager(chatRoomDao, daoManager.getUserDao());

        boolean success = chatRoomManager.sendChatMessage(chatRoom.getId(), user1.getId(), new ChatMessage("cool message"));
        assertTrue(success);

        success = chatRoomManager.sendChatMessage(chatRoom.getId(), user2.getId(), new ChatMessage("haai"));
        assertTrue(success);

        success = chatRoomManager.sendChatMessage(chatRoom.getId(), user1.getId(), new ChatMessage("doeei"));
        assertTrue(success);
    }
}
