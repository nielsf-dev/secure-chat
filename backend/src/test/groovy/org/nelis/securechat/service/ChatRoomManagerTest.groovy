package org.nelis.securechat.service


import org.nelis.securechat.domain.ChatMessage
import org.nelis.securechat.domain.ChatRoom
import org.nelis.securechat.domain.User
import org.nelis.securechat.service.blocking.ChatRoomManager
import org.nelis.securechat.service.blocking.dao.ChatRoomDao
import org.nelis.securechat.service.blocking.dao.UserDao
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
