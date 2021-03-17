package org.nelis.integration;

import org.junit.jupiter.api.Test;
import org.nelis.domain.ChatMessage;
import org.nelis.service.blocking.ChatManager;
import org.nelis.service.blocking.dao.DaoManager;

import static org.junit.jupiter.api.Assertions.*;

public class ChatManagerTest {
    @Test
    public void test(){
        DaoManager daoManager = new DaoManager();

        ChatManager chatManager = new ChatManager(daoManager.getChatRoomDao(),
                daoManager.getChatRoomMessageDao());

        chatManager.sendChatMessage(1,1,new ChatMessage("Test"));
    }
}
