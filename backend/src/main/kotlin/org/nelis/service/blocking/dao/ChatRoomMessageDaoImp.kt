package org.nelis.service.blocking.dao

import org.hibernate.SessionFactory
import org.nelis.domain.ChatRoomMessage

class ChatRoomMessageDaoImp(private val sessionFactory: SessionFactory) : AbstractHibernateDao<ChatRoomMessage>(sessionFactory,ChatRoomMessage::class.java), ChatRoomMessageDao {

}