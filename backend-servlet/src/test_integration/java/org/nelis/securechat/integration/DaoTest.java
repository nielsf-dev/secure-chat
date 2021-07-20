package org.nelis.securechat.integration;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.nelis.securechat.service.blocking.DaoRegistry;
import org.nelis.securechat.service.blocking.DaoRegistryImp;
import org.nelis.securechat.service.blocking.SessionFactoryBuilder;
import org.nelis.securechat.service.blocking.dao.ChatRoomDao;

public class DaoTest {

    @Test
    public void test(){
        SessionFactory sessionFactory = new SessionFactoryBuilder().build();
        DaoRegistry registry = new DaoRegistryImp(sessionFactory);
        ChatRoomDao chatRoomDao = registry.getChatRoomDao();


    }
}
