package org.nelis.service.blocking.dao

import org.hibernate.SessionFactory
import org.nelis.domain.ChatRoomMessage

class ChatRoomMessageDaoH(val sessionFactory: SessionFactory) : ChatRoomMessageDao {

    override fun save(chatRoomMessage: ChatRoomMessage) {
        TODO("Not yet implemented")
    }
}