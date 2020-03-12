package com.app.greenveg.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(),version = 1)
abstract class AppDatabase:RoomDatabase() {

    abstract fun cartDao(): CartDao
    
}