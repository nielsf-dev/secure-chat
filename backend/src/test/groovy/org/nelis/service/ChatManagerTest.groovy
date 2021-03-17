package org.nelis.service

import org.nelis.domain.ChatMessage
import org.nelis.domain.ChatRoom
import org.nelis.domain.User
import org.nelis.service.blocking.ChatManager
import org.nelis.service.blocking.dao.ChatRoomDao
import org.nelis.service.blocking.dao.ChatRoomMessageDao
import spock.lang.Specification

class ChatManagerTest extends Specification {

    def chatRoom = new ChatRoom("chatroom");
    def chatRoomDao = Mock(ChatRoomDao)
    def chatRoomMessageDao = Mock(ChatRoomMessageDao)
    def chatManager = new ChatManager(chatRoomDao, chatRoomMessageDao)

    def setup(){
        chatRoom.insertUser(new User(1,"niels"))
        chatRoom.insertUser(new User(2,"hans"))
    }

    def "Basic chat"(){
        given:
        chatRoomDao.getChatRoomById(1) >> chatRoom

        when:
        chatManager.sendChatMessage(1, 1, new ChatMessage("chatting"))

        then:
        1 * chatRoomMessageDao.save(_)
    }
}
