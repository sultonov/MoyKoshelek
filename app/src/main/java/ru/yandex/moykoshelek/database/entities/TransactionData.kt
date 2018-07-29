package ru.yandex.moykoshelek.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import ru.yandex.moykoshelek.getCurrentDateTime
import ru.yandex.moykoshelek.toString

@Entity(tableName = "transactions")
data class TransactionData(@PrimaryKey(autoGenerate = true) var id: Long?,
                           @ColumnInfo(name = "created_dt") var time: String,
                           @ColumnInfo(name = "cost") var cost: Double,
                           @ColumnInfo(name = "currency") var currency: String,
                           @ColumnInfo(name = "placeholder") var placeholder: String,
                           @ColumnInfo(name = "type_transaction") var typeTransaction: String

){
    constructor():this(null, getCurrentDateTime().toString("yyyy/MM/dd HH:mm:ss"),0.0,"RUB","","in")
}