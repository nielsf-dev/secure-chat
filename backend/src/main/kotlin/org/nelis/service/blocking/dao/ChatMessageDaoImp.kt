package org.nelis.service.blocking.dao

import org.hibernate.SessionFactory
import org.nelis.domain.ChatMessage

class ChatMessageDaoImp(sessionFactory: SessionFactory) : AbstractHibernateDao<ChatMessage>(sessionFactory, ChatMessage::class.java), ChatMessageDao {
}