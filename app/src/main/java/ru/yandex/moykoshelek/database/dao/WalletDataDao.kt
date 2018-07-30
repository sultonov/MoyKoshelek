package ru.yandex.moykoshelek.database.dao

import android.arch.persistence.room.*
import ru.yandex.moykoshelek.database.entities.WalletData

@Dao
interface WalletDataDao {
    @Query("SELECT * from wallets")
    fun getAll(): List<WalletData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(walletData: WalletData)

    @Update
    fun update(wallet: WalletData)
}