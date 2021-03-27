package org.nelis.securechat.service.blocking.servlet

import com.fasterxml.jackson.databind.ObjectMapper
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.Transaction
import org.nelis.securechat.service.blocking.ChatRoomManager
import spock.lang.Specification

import javax.servlet.ServletException
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class ChatServletTest extends Specification {

    def sessionFactory = Mock(SessionFactory)
    def session = Mock(Session)
    def tx = Mock(Transaction)

    def commands = new ArrayList<ChatServletCommand>()
    def chatServlet = new ChatServlet(commands)
    def servletResponse = Mock(ServletResponse)
    def stringWriter = new StringWriter()
    def stringReader = new StringReader("{\"name\":\"mycoolroom\"}")

    def setup() {
        commands.add(new CreateRoom(Mock(ChatRoomManager),new ObjectMapper(), sessionFactory))
        servletResponse.getWriter() >> new PrintWriter(stringWriter)

        sessionFactory.getCurrentSession() >> session
        session.beginTransaction() >> tx
    }

    def "Succesvolle command"(){
        given:
        def servletRequest = Mock(HttpServletRequest)
        servletRequest.getRequestURI() >> "/createroom"
        servletRequest.getReader() >> new BufferedReader(stringReader)

        when:
        chatServlet.service(servletRequest, servletResponse)

        then:
        notThrown()
    }

    def "Foute command"(){
        given:
        def servletRequest = Mock(HttpServletRequest)
        servletRequest.getRequestURI() >> "/bollocks2"
        servletRequest.getReader() >> new BufferedReader(stringReader)

        when:
        chatServlet.service(servletRequest, servletResponse)

        then:
        thrown(ServletException)
    }
}