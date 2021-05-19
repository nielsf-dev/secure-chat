package org.nelis.securechat.service.blocking.servlet.commands


import com.fasterxml.jackson.databind.ObjectMapper
import ChatMessage
import ChatRoom
import User
import org.nelis.securechat.service.blocking.dao.ChatRoomDao
import spock.lang.Specification

class ShowMessagesCommandTest extends Specification {
    def objectMapper = new ObjectMapper();
    def chatRoomDao = Mock(ChatRoomDao);
    def showMessagesCommand = new ShowMessagesCommand(chatRoomDao, objectMapper);

    def setup(){
        def chatRoom = new ChatRoom("yolo");
        def user1 = new User(1, "niels")
        def user2 = new User(2, "riels")

        chatRoom.insertUser(user1)
        chatRoom.insertUser(user2)

        chatRoom.sendMessage(1, new ChatMessage("hoi"))
        chatRoom.sendMessage(2, new ChatMessage("doe"))
        chatRoom.sendMessage(1, new ChatMessage("awsdf"))

        chatRoomDao.find(_) >> chatRoom
    }

    def "Show messages"(){
        when:
        def response = showMessagesCommand.getResponse("{\"chatroomid\":1}")
        def responseNode = objectMapper.readTree(response)

        then:
        !response.equalsIgnoreCase("")
        responseNode.get("messages").size() == 3
    }

}
