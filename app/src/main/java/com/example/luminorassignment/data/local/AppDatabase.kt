package com.example.luminorassignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.luminorassignment.data.local.user.User
import com.example.luminorassignment.data.local.user.UserDao

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
