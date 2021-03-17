package org.nelis.service.blocking

import org.nelis.domain.ChatMessage
import org.nelis.service.blocking.dao.ChatRoomDao
import org.nelis.service.blocking.dao.ChatRoomMessageDao
import org.slf4j.LoggerFactory
import java.lang.Exception

class ChatManager(private val chatRoomDao: ChatRoomDao, private val chatRoomMessageDao: ChatRoomMessageDao) {

    private val logger = LoggerFactory.getLogger(ChatManager::class.java)

    fun sendChatMessage(roomId: Int, userId: Int, chatMessage: ChatMessage?): Boolean {
        return try {
            val chatRoom = chatRoomDao.getChatRoomById(roomId)
            val chatRoomMessage = chatRoom.sendMessage(userId, chatMessage)
            chatRoomMessageDao.save(chatRoomMessage)
            true
        } catch (ex: Exception) {
            logger.error("Error bij versturen ChatMessage", ex)
            false
        }
    }
}