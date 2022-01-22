package ru.pinguin.library.database

import ru.pinguin.library.database.dao.UserDao

class UsersRepository(private val userDao: UserDao) {

    fun getUser() : String? = userDao.getUser()

    fun deleteUser() = userDao.deleteUser()

    fun saveUser(user: User) = userDao.saveUser(user)
}