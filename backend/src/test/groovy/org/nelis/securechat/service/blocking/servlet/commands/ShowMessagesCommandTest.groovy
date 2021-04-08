package org.nelis.securechat.service.blocking.servlet.commands

import com.fasterxml.jackson.databind.ObjectMapper
import org.nelis.securechat.domain.ChatMessage
import org.nelis.securechat.domain.ChatRoom
import org.nelis.securechat.domain.ChatRoomMessage
import org.nelis.securechat.domain.User
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

        chatRoom.messages.add(new ChatRoomMessage(chatRoom, user1, new ChatMessage("hoi")))
        chatRoom.messages.add(new ChatRoomMessage(chatRoom, user2, new ChatMessage("doi")))
        chatRoom.messages.add(new ChatRoomMessage(chatRoom, user1, new ChatMessage("dpeooi")))

        chatRoomDao.find(_) >> chatRoom
    }

    def "Show messages"(){
        when:
        def response = showMessagesCommand.getResponse("{\"chatroomid\":1}")

        then:
        !response.equalsIgnoreCase("")
    }

}
