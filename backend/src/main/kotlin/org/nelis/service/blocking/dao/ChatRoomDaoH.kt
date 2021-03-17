package org.nelis.service.blocking.dao

import org.hibernate.SessionFactory
import org.nelis.domain.ChatRoom

class ChatRoomDaoH(val sessionFactory: SessionFactory) : ChatRoomDao {
    override fun getChatRoomById(chatRoomId: Int): ChatRoom {
        TODO("Not yet implemented")
    }
}