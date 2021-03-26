package org.nelis.securechat.service.blocking.servlet

import com.fasterxml.jackson.databind.ObjectMapper
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.Transaction
import spock.lang.Specification

class CreateRoomTest extends Specification {
    def objectMapper = new ObjectMapper()
    def chatRoomManager = Mock(org.nelis.securechat.service.blocking.IChatRoomManager)
    def sessionFactory = Mock(SessionFactory)
    def session = Mock(Session)
    def tx = Mock(Transaction)
    def createRoom = new CreateRoom(chatRoomManager, objectMapper, sessionFactory)

    def setup(){
        sessionFactory.getCurrentSession() >> session
        session.beginTransaction() >> tx
    }

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
