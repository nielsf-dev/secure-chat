package org.nelis.securechat.service.blocking.dao

import org.nelis.securechat.domain.ChatRoom

interface ChatRoomDao : Dao<org.nelis.securechat.domain.ChatRoom> {
    fun findByName(name:String): org.nelis.securechat.domain.ChatRoom?
}