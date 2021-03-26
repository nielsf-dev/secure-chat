package org.nelis.service.blocking.servlet

import com.fasterxml.jackson.databind.ObjectMapper
import org.nelis.service.blocking.ChatRoomManager
import org.nelis.service.blocking.IChatRoomManager
import org.nelis.service.blocking.dao.ChatRoomDao
import org.nelis.service.blocking.dao.UserDao
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

class CreateRoomTest extends Specification {
    def objectMapper = new ObjectMapper()
    def chatRoomManager = Mock(IChatRoomManager)
    def createRoom = new CreateRoom(chatRoomManager, objectMapper)

    def "CreatRoom (correct)"(){
        given:
        def parseLong = Long.parseLong("1")
        chatRoomManager.createChatRoom(_) >> parseLong

        when:
        def response = createRoom.getResponse("{\"name\":\"mycoolroom\"}")

        then:
        response == "{\"id\":1}"
    }
}
