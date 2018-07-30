package ru.yandex.moykoshelek.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import ru.yandex.moykoshelek.database.entities.WalletData

@Dao
interface WalletDataDao {
    @Query("SELECT * from wallets")
    fun getAll(): List<WalletData>

    @Insert()
    fun insert(walletData: WalletData)
}