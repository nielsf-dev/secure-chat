package org.nelis.securechat.service.blocking.dao

import org.nelis.securechat.domain.User

interface UserDao : Dao<User> {
    fun findByName(name:String):User?
}