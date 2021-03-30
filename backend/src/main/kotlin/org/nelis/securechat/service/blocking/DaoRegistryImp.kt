package org.nelis.securechat.service.blocking

import org.hibernate.SessionFactory
import org.nelis.securechat.service.blocking.dao.ChatRoomDao
import org.nelis.securechat.service.blocking.dao.ChatRoomDaoImp
import org.nelis.securechat.service.blocking.dao.UserDao
import org.nelis.securechat.service.blocking.dao.UserDaoImp

class DaoRegistryImp : DaoRegistry {

    override val chatRoomDao: ChatRoomDao
    override val userDao: UserDao

    constructor(sessionFactory:SessionFactory){
        chatRoomDao = ChatRoomDaoImp(sessionFactory)
        userDao = UserDaoImp(sessionFactory)
    }
}