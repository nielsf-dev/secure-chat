package org.nelis.securechat.service.blocking.dao

import org.nelis.securechat.domain.ChatRoom

interface ChatRoomDao : HibernateDao<ChatRoom> {
    fun findByName(name:String):ChatRoom
}