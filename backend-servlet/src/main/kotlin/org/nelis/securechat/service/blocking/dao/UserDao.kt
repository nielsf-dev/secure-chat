package org.nelis.securechat.service.blocking.dao

import org.nelis.securechat.domain.User

interface UserDao : Dao<org.nelis.securechat.domain.User> {
    fun findByName(name:String): org.nelis.securechat.domain.User?
}