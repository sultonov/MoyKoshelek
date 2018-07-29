package ru.yandex.moykoshelek.database.entities

import android.arch.persistence.room.*
import ru.yandex.moykoshelek.getCurrentDateTime
import ru.yandex.moykoshelek.toString

@Entity(tableName = "wallets")
data class WalletData(@PrimaryKey(autoGenerate = true) var id: Long?,
                      @ColumnInfo(name = "name") var time: String,
                      @ColumnInfo(name = "balance") var balance: Double,
                      @ColumnInfo(name = "currency") var currency: String,
                      @ColumnInfo(name = "number") var number: String,
                      @ColumnInfo(name = "date") var date: String

){
    constructor():this(null, getCurrentDateTime().toString("yyyy/MM/dd HH:mm:ss"),0.0,"RUB","","12/12")
}