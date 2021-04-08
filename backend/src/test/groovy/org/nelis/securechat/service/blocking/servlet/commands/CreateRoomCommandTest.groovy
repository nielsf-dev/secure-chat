package org.nelis.securechat.service.blocking.servlet.commands

import com.fasterxml.jackson.databind.ObjectMapper
import org.nelis.securechat.service.blocking.ChatRoomManager
import org.nelis.securechat.service.blocking.dao.ChatRoomDao
import org.nelis.securechat.service.blocking.dao.UserDao
import org.nelis.securechat.service.blocking.servlet.commands.CreateRoomCommand
import spock.lang.Specification

class CreateRoomCommandTest extends Specification {
    def objectMapper = new ObjectMapper()
    def userDao = Mock(UserDao)
    def chatroomDao = Mock(ChatRoomDao)
    def chatRoomManager = new ChatRoomManager(chatroomDao, userDao)
    def createRoom = new CreateRoomCommand(chatRoomManager, objectMapper)

    def setup(){
    }

    def "CreatRoom (correct)"(){
        when:
        def response = createRoom.getResponse("{\"name\":\"mycoolroom\"}")

        then:
        response == "{\"id\":0}"
        1 * chatroomDao.save(_)
    }
}
