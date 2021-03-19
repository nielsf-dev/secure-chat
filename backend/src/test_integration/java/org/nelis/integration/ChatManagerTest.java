package org.nelis.integration;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import org.nelis.domain.ChatMessage;
import org.nelis.domain.User;
import org.nelis.service.blocking.ChatManager;
import org.nelis.service.blocking.dao.DaoManager;

import static org.junit.jupiter.api.Assertions.*;

public class ChatManagerTest {
    @Test
    public void test(){
        DaoManager daoManager = new DaoManager();
        Session session = daoManager.getSessionFactory().openSession();


        Transaction tx = session.beginTransaction();
        User user = new User("relis");
        session.save(user);
        session.flush();
        tx.commit();

//        ChatManager chatManager = new ChatManager(daoManager.getChatRoomDao(),
//                daoManager.getChatRoomMessageDao());
//
//        chatManager.sendChatMessage(1,1,new ChatMessage("Test"));
    }
}
