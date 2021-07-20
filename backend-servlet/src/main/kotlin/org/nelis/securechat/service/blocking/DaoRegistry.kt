package org.nelis.securechat.service.blocking

import org.nelis.securechat.service.blocking.dao.ChatRoomDao
import org.nelis.securechat.service.blocking.dao.UserDao

interface DaoRegistry{
    val chatRoomDao: ChatRoomDao
    val userDao: UserDao
}