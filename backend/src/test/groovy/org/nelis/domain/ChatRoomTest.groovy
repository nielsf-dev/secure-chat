package org.nelis.domain

import javassist.NotFoundException
import spock.lang.Specification

class ChatRoomTest extends Specification {
    def chatRoom = new ChatRoom("mycoolsexychatroom")
    def user1 = new User(1, "Niels")
    def user2 = new User(2, "Reals")

    def setup(){
        chatRoom.insertUser(user1)
        chatRoom.insertUser(user1)
        chatRoom.insertUser(user2)
    }

    def "Gebruikers bekend in chatroom"(){
        expect:
        chatRoom.users.size() == 2
    }

    def "Bericht sturen(goed)"(){
        when:
        def result = chatRoom.sendMessage(1, new ChatMessage("test"))

        then:
        result != null
        chatRoom.messages.size() == 1
    }

    def "Bericht sturen(fout)"(){
        when:
        chatRoom.sendMessage(3,new ChatMessage("test"))

        then:
        thrown(NotFoundException)
    }
}
