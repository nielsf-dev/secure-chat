package org.nelis.service.blocking.dao

import org.hibernate.SessionFactory
import org.nelis.domain.ChatRoom

class ChatRoomDaoImp(private val sessionFactory: SessionFactory) : AbstractHibernateDao<ChatRoom>(sessionFactory, ChatRoom::class.java), ChatRoomDao {

}