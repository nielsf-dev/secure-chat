package org.nelis.service

import org.nelis.domain.ChatMessage
import org.nelis.domain.ChatRoom
import org.nelis.domain.User
import org.nelis.service.blocking.ChatRoomManager
import org.nelis.service.blocking.dao.ChatRoomDao
import org.nelis.service.blocking.dao.UserDao
import spock.lang.Specification

class ChatRoomManagerTest extends Specification {

    def chatRoom = new ChatRoom("chatroom");
    def chatRoomDao = Mock(ChatRoomDao)
    def userDao = Mock(UserDao)
    def chatManager = new ChatRoomManager(chatRoomDao, userDao)

    def setup(){
        chatRoom.insertUser(new User(1,"niels"))
        chatRoom.insertUser(new User(2,"hans"))
    }

    def "Basic chat"(){
        given:
        chatRoomDao.find(1) >> chatRoom

        when:
        def success = chatManager.sendChatMessage(1, 1, new ChatMessage("chatting"))

        then:
        success
        chatRoom.messages.size() == 1
    }
}
