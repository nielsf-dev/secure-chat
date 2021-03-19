package org.nelis.service

import org.nelis.domain.ChatMessage
import org.nelis.domain.ChatRoom
import org.nelis.domain.User
import org.nelis.service.blocking.ChatManager
import org.nelis.service.blocking.dao.ChatMessageDao
import org.nelis.service.blocking.dao.ChatRoomDao
import org.nelis.service.blocking.dao.ChatRoomMessageDao
import spock.lang.Specification

class ChatManagerTest extends Specification {

    def chatRoom = new ChatRoom("chatroom");
    def chatRoomDao = Mock(ChatRoomDao)
    def chatRoomMessageDao = Mock(ChatRoomMessageDao)
    def chatMessageDao = Mock(ChatMessageDao)
    def chatManager = new ChatManager(chatRoomDao, chatRoomMessageDao, chatMessageDao)

    def setup(){
        chatRoom.insertUser(new User(1,"niels"))
        chatRoom.insertUser(new User(2,"hans"))
    }

    def "Basic chat"(){
        given:
        chatRoomDao.find(1) >> chatRoom

        when:
        def success = chatManager.sendChatMessage(1, 1, new ChatMessage("chatting"))

        then:
        success
        1 * chatRoomMessageDao.save(_)
    }
}
