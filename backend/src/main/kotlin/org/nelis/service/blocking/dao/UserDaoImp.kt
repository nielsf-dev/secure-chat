package org.nelis.service.blocking.dao

import org.hibernate.SessionFactory
import org.nelis.domain.User

class UserDaoImp(private val sessionFactory: SessionFactory) : AbstractHibernateDao<User>(sessionFactory, User::class.java), UserDao {
}