package com.app.greenveg.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val Lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(Lock) {
            instance ?: buildDatabase(context).also {
                instance=it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "mydatabase.db").build()

    }

}
