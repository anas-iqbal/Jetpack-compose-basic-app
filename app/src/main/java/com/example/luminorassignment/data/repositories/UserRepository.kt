package com.example.luminorassignment.data.repositories

import com.example.luminorassignment.data.local.user.User
import com.example.luminorassignment.data.local.user.UserDao
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    suspend fun registerUser(user: User) = userDao.insertUser(user)
    suspend fun getUserByEmail(email: String): User? = userDao.getUserByEmail(email)
}
