package org.nelis.securechat.service.blocking.dao

import org.hibernate.SessionFactory
import org.nelis.securechat.domain.User

class UserDaoImp(private val sessionFactory: SessionFactory) : AbstractHibernateDao<org.nelis.securechat.domain.User>(sessionFactory, org.nelis.securechat.domain.User::class.java),
    UserDao {
    override fun findByName(name: String): org.nelis.securechat.domain.User? {
        val createQuery = sessionFactory.currentSession.createQuery("from User u where u.name = ?1")
        createQuery.setParameter(1, name);
        return createQuery.uniqueResult() as org.nelis.securechat.domain.User?
    }
}