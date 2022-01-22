package ru.pinguin.library.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.pinguin.library.database.User

@Dao
interface UserDao {

    @Query("select name from user limit 1")
    fun getUser(): String?

    @Query("delete from user where 1=1")
    fun deleteUser()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: User)

}