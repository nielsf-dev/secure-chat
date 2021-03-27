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

    def "Basic chat"(){
        given:
        chatRoom.insertUser(new User(1,"niels"))
        chatRoom.insertUser(new User(2,"hans"))
        chatRoomDao.find(1) >> chatRoom

        when:
        def success = chatManager.sendChatMessage(1, 1, new ChatMessage("chatting"))

        then:
        success
        chatRoom.messages.size() == 1
    }

    def "User aanmaken alleen als die nog niet bestaat"(){
        given:
        userDao.findByName("niels") >> new User("niels")
        userDao.findByName("hans") >> null

        when:
        def user = chatManager.createUser("niels")

        then:
        0 * userDao.save(_)

        when:
        chatManager.createUser("hans")

        then:
        1 * userDao.save(_)
    }

    def "Chatroom aanmaken alleen als die nog niet bestaat"(){
        given:
        chatRoomDao.findByName("myroom") >> new ChatRoom("myroom")
        chatRoomDao.findByName("otherroom") >> null

        when:
        chatManager.createChatRoom("myroom")

        then:
        0 * chatRoomDao.save(_)

        when:
        chatManager.createChatRoom("otherroom")

        then:
        1 * chatRoomDao.save(_)
    }
}
