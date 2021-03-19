package org.nelis.service.blocking.dao

import org.nelis.domain.ChatRoom

interface ChatRoomDao : HibernateDao<ChatRoom> {
    fun getChatRoomById(chatRoomId: Int): ChatRoom
}