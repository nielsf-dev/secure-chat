package org.nelis.securechat.service


import ChatMessage
import ChatRoom
import User
import org.nelis.securechat.service.blocking.ChatRoomManager
import org.nelis.securechat.service.blocking.dao.ChatRoomDao
import org.nelis.securechat.service.blocking.dao.UserDao
import spock.lang.Specification

class ChatRoomManagerTest extends Specification {

    def chatRoom = new ChatRoom("chatroom");
    def chatRoomDao = Mock(ChatRoomDao)
    def userDao = Mock(UserDao)
    def chatRoomManager = new ChatRoomManager(chatRoomDao, userDao)

    def "Basic chat"(){
        given:
        chatRoom.insertUser(new User(1,"niels"))
        chatRoom.insertUser(new User(2,"hans"))
        chatRoomDao.find(1) >> chatRoom

        when:
        def success = chatRoomManager.sendChatMessage(1, 1, new ChatMessage("chatting"))

        then:
        success
        chatRoom.messages.size() == 1
    }

    def "User aanmaken alleen als die nog niet bestaat"(){
        given:
        userDao.findByName("niels") >> new User("niels")
        userDao.findByName("hans") >> null

        when:
        def user = chatRoomManager.createUser("niels")

        then:
        0 * userDao.save(_)

        when:
        chatRoomManager.createUser("hans")

        then:
        1 * userDao.save(_)
    }

    def "Chatroom aanmaken alleen als die nog niet bestaat"(){
        given:
        chatRoomDao.findByName("myroom") >> new ChatRoom("myroom")
        chatRoomDao.findByName("otherroom") >> null

        when:
        chatRoomManager.createChatRoom("myroom")

        then:
        0 * chatRoomDao.save(_)

        when:
        chatRoomManager.createChatRoom("otherroom")

        then:
        1 * chatRoomDao.save(_)
    }
}
