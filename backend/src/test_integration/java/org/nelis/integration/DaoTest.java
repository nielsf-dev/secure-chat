package org.nelis.integration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nelis.domain.ChatMessage;
import org.nelis.domain.ChatRoom;
import org.nelis.domain.User;
import org.nelis.service.blocking.ChatManager;
import org.nelis.service.blocking.dao.ChatRoomDao;
import org.nelis.service.blocking.dao.DaoManager;
import org.nelis.service.blocking.dao.UserDao;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DaoTest {

    private static DaoManager daoManager;
    private static Transaction tx;

    public DaoTest() {
    }

    @BeforeAll
    static void beforeAll() {
        daoManager = new DaoManager();

        SessionFactory sessionFactory = daoManager.getSessionFactory();
        Session currentSession = sessionFactory.getCurrentSession();
        tx = currentSession.beginTransaction();
    }

    @AfterAll
    static void afterAll() {
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
    void testChatRoom() {
        ChatRoomDao chatRoomDao = daoManager.getChatRoomDao();
        UserDao userDoa = daoManager.getUserDao();

        List<User> all = userDoa.findAll();
        ChatRoom chatRoom = new ChatRoom("test");
        chatRoom.insertUser(all.get(0));
        chatRoom.insertUser(all.get(1));

        chatRoomDao.save(chatRoom);

        ChatRoom found = chatRoomDao.find(chatRoom.getId());
        assertNotNull(found);
    }
}
