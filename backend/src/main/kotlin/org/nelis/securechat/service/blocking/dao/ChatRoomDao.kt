package org.nelis.securechat.service.blocking.dao

import org.nelis.securechat.domain.ChatRoom

interface ChatRoomDao : Dao<ChatRoom> {
    fun findByName(name:String):ChatRoom?
}