package ru.yandex.moykoshelek.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import ru.yandex.moykoshelek.database.entities.TransactionData

@Dao
interface TransactionDataDao {

    @Query("SELECT * from transactions")
    fun getAll(): List<TransactionData>

    @Insert()
    fun insert(weatherData: TransactionData)
}