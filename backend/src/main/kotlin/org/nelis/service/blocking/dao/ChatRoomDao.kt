package org.nelis.service.blocking.dao

import org.nelis.domain.ChatRoom

interface ChatRoomDao {
    fun getChatRoomById(chatRoomId: Int): ChatRoom
}