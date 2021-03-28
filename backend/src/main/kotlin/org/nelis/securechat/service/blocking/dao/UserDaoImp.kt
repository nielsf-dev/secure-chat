package org.nelis.securechat.service.blocking.dao

import org.hibernate.SessionFactory
import org.nelis.securechat.domain.User

class UserDaoImp(private val sessionFactory: SessionFactory) : AbstractHibernateDao<User>(sessionFactory, User::class.java),
    UserDao {
    override fun findByName(name: String): User? {
        val createQuery = sessionFactory.currentSession.createQuery("from User u where u.name = ?1")
        createQuery.setParameter(1, name);
        return createQuery.uniqueResult() as User?
    }
}