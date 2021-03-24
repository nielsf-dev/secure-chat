package org.nelis.service.blocking

import org.nelis.domain.ChatMessage
import org.nelis.domain.ChatRoom
import org.nelis.domain.User
import org.nelis.service.blocking.dao.ChatRoomDao
import org.nelis.service.blocking.dao.UserDao
import org.slf4j.LoggerFactory
import java.lang.Exception

class ChatRoomManager(private val chatRoomDao: ChatRoomDao,
                      private val userDao: UserDao) {

    private val logger = LoggerFactory.getLogger(ChatRoomManager::class.java)

    fun createChatRoom(name: String):Long{
        val chatRoom = ChatRoom(name)
        chatRoomDao.save(chatRoom)
        return chatRoom.id
    }

    fun createUser(name: String):Long{
        val user = User(name)
        userDao.save(user)
        return user.id
    }

    fun addUserToRoom(roomId:Long, userId: Long){
        val room = chatRoomDao.find(roomId)
        val user = userDao.find(userId)
        room.insertUser(user)
    }

    fun sendChatMessage(roomId: Long, userId: Long, chatMessage: ChatMessage?): Boolean {
        return try {
            val chatRoom = chatRoomDao.find(roomId)
            chatRoom.sendMessage(userId, chatMessage)
            true
        } catch (ex: Exception) {
            logger.error("Error bij versturen ChatMessage", ex)
            false
        }
    }
}