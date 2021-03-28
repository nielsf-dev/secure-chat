package org.nelis.securechat.service.blocking.servlet

import com.fasterxml.jackson.databind.ObjectMapper
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.Transaction
import org.nelis.securechat.service.blocking.ChatRoomManager
import org.nelis.securechat.service.blocking.dao.ChatRoomDao
import org.nelis.securechat.service.blocking.dao.UserDao
import spock.lang.Specification

class CreateRoomTest extends Specification {
    def objectMapper = new ObjectMapper()
    def userDao = Mock(UserDao)
    def chatroomDao = Mock(ChatRoomDao)
    def chatRoomManager = new ChatRoomManager(chatroomDao, userDao)
    def sessionFactory = Mock(SessionFactory)
    def session = Mock(Session)
    def tx = Mock(Transaction)
    def createRoom = new CreateRoom(chatRoomManager, objectMapper, sessionFactory)

    def setup(){
        sessionFactory.getCurrentSession() >> session
        session.beginTransaction() >> tx
    }

    def "CreatRoom (correct)"(){
        when:
        def response = createRoom.getResponse("{\"name\":\"mycoolroom\"}")

        then:
        response == "{\"id\":0}"
        1 * chatroomDao.save(_)
    }
}
