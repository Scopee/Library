package ru.pinguin.library.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(@PrimaryKey @ColumnInfo(name = "name") var name: String)
