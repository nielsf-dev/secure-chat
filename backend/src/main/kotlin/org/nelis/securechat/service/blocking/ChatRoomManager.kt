package org.nelis.securechat.service.blocking

import org.nelis.securechat.domain.ChatMessage
import org.nelis.securechat.domain.ChatRoom
import org.nelis.securechat.domain.User
import org.nelis.securechat.service.blocking.dao.ChatRoomDao
import org.nelis.securechat.service.blocking.dao.UserDao
import org.slf4j.LoggerFactory

/**
 * Manament functionaliteit omtrend chat rooms
 */
open class ChatRoomManager(private val chatRoomDao: ChatRoomDao,
                           private val userDao: UserDao) {

    private val logger = LoggerFactory.getLogger(ChatRoomManager::class.java)

    /**
     * Maak een chatroom aan als die nog niet bestaat
     */
    fun createChatRoom(name: String):Long{
        var chatRoom = chatRoomDao.findByName(name)
        if(chatRoom == null) {
            chatRoom = ChatRoom(name)
            chatRoomDao.save(chatRoom)
        }
        return chatRoom.id
    }

    /**
     * Maak een user aan als die nog niet bestaat
     */
    fun createUser(name: String):Long{
        var user:User = userDao.findByName(name)
        if(user == null) {
            user = User(name)
            userDao.save(user)
        }
        return user.id
    }

    /**
     * Voeg een user toe aan een room, als de user er al in zit word er niks gedaan
     */
    fun addUserToRoom(roomId:Long, userId: Long){
        val room = chatRoomDao.find(roomId)
        val user = userDao.find(userId)
        room.insertUser(user)
    }

    /**
     * Verstuur een bericht in een room
     */
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