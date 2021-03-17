package org.nelis.service.blocking.dao

import org.nelis.domain.ChatRoomMessage

interface ChatRoomMessageDao {
    fun save(chatRoomMessage: ChatRoomMessage)
}