package org.nelis.securechat.domain

import org.nelis.securechat.domain.ChatMessage
import spock.lang.Specification

class ChatMessageTest extends Specification {
    def "Test secret"(){
        when:
        def chatMessage = new ChatMessage("test")
        def here = chatMessage.getMessage()
        here = "something else"

        then:
        chatMessage.getMessage() == "test"
    }
}
