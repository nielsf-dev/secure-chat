package org.nelis.securechat.service.blocking

import org.nelis.securechat.domain.ChatMessage
import org.nelis.securechat.domain.ChatRoom
import org.nelis.securechat.domain.User
import org.nelis.securechat.service.blocking.dao.ChatRoomDao
import org.nelis.securechat.service.blocking.dao.UserDao
import org.slf4j.LoggerFactory

open class ChatRoomManager(private val chatRoomDao: ChatRoomDao,
                           private val userDao: UserDao
) : IChatRoomManager {

    private val logger = LoggerFactory.getLogger(ChatRoomManager::class.java)

    override fun createChatRoom(name: String):Long{
        val chatRoom = ChatRoom(name)
        chatRoomDao.save(chatRoom)
        return chatRoom.id
    }

    override fun createUser(name: String):Long{
        val user = User(name)
        userDao.save(user)
        return user.id
    }

    override fun addUserToRoom(roomId:Long, userId: Long){
        val room = chatRoomDao.find(roomId)
        val user = userDao.find(userId)
        room.insertUser(user)
    }

    override fun sendChatMessage(roomId: Long, userId: Long, chatMessage: ChatMessage?): Boolean {
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