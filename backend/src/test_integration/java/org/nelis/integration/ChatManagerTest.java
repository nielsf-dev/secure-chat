package org.nelis.integration;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import org.nelis.domain.ChatMessage;
import org.nelis.domain.User;
import org.nelis.service.blocking.ChatManager;
import org.nelis.service.blocking.dao.DaoManager;
import org.nelis.service.blocking.dao.UserDao;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChatManagerTest {
    @Test
    public void test(){
        DaoManager daoManager = new DaoManager();
        UserDao userDao = daoManager.getUserDao();

        userDao.save(new User("Het werkt jongee"));

        List<User> all = userDao.findAll();
        all.stream().forEach(user -> System.out.println(user.getName()));



//        ChatManager chatManager = new ChatManager(daoManager.getChatRoomDao(),
//                daoManager.getChatRoomMessageDao());
//
//        chatManager.sendChatMessage(1,1,new ChatMessage("Test"));
    }
}
