package org.nelis.securechat.service.blocking.dao

import org.hibernate.SessionFactory
import org.nelis.securechat.domain.ChatRoom

class ChatRoomDaoImp(private val sessionFactory: SessionFactory) : AbstractHibernateDao<org.nelis.securechat.domain.ChatRoom>(sessionFactory, org.nelis.securechat.domain.ChatRoom::class.java),
    ChatRoomDao {
    override fun findByName(name: String): org.nelis.securechat.domain.ChatRoom? {
        val query = sessionFactory.currentSession.createQuery("from ChatRoom c where c.name = ?1")
        query.setParameter(1, name);
        return query.uniqueResult() as org.nelis.securechat.domain.ChatRoom?
    }

}