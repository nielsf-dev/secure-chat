package org.nelis.integration;

import javassist.NotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.nelis.domain.ChatMessage;
import org.nelis.domain.ChatRoom;
import org.nelis.domain.User;
import org.nelis.service.blocking.ChatRoomManager;
import org.nelis.service.blocking.dao.ChatRoomDao;
import org.nelis.service.blocking.dao.DaoManager;
import org.nelis.service.blocking.dao.UserDao;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DaoTest {

    private static DaoManager daoManager;
    private static Session currentSession;

    @BeforeAll
    static void beforeAll() {
        daoManager = new DaoManager();

        SessionFactory sessionFactory = daoManager.getSessionFactory();
        currentSession = sessionFactory.getCurrentSession();
    }

    @Test
    public void testUser(){
        Transaction tx = currentSession.beginTransaction();
        UserDao userDao = daoManager.getUserDao();

        User newUser = new User("Het werkt jongee");
        userDao.save(newUser);

        List<User> all = userDao.findAll();
        all.stream().forEach(user -> System.out.println(user.getName()));

        User user = userDao.find(newUser.getId());
        tx.commit();
        assertNotNull(user);
    }

    @Test
    void relationTest() throws NotFoundException {
        Transaction tx = currentSession.beginTransaction();
        ChatRoomDao chatRoomDao = daoManager.getChatRoomDao();

        ChatRoom chatRoom = chatRoomDao.find(3);
        User firstUser = chatRoom.getUsers().stream().findFirst().get();
        chatRoom.sendMessage(firstUser.getId(),new ChatMessage("yolings"));

        tx.commit();
    }

    @Test
    void testChatRoom() {
        Transaction tx = currentSession.beginTransaction();

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

        tx.commit();
    }
}
