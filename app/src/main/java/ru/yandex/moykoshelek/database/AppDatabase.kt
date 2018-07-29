package ru.yandex.moykoshelek.database

import android.content.Context
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import ru.yandex.moykoshelek.database.dao.TransactionDataDao
import ru.yandex.moykoshelek.database.entities.TransactionData

@Database(entities = arrayOf(TransactionData::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionDataDao(): TransactionDataDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(TransactionData::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase::class.java, "app.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}