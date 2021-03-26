package org.nelis.securechat.service.blocking.dao

import org.hibernate.SessionFactory
import org.nelis.securechat.domain.User

class UserDaoImp(private val sessionFactory: SessionFactory) : AbstractHibernateDao<User>(sessionFactory, User::class.java),
    UserDao {
}