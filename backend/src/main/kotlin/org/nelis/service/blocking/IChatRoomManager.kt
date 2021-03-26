package org.nelis.service.blocking

import org.nelis.domain.ChatMessage

public interface IChatRoomManager {
    open fun createChatRoom(name: String): Long
    fun createUser(name: String): Long
    fun addUserToRoom(roomId: Long, userId: Long)
    fun sendChatMessage(roomId: Long, userId: Long, chatMessage: ChatMessage?): Boolean
}