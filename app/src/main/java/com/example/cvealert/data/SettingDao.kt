package com.example.cvealert.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SettingDao {
    @Insert
    fun insert(setting: Setting)

    @Delete
    fun delete(setting: Setting)

    @Query("SELECT * FROM Setting")
    fun getAll(): LiveData<List<Setting>>

    @Update
    fun updateUser(setting: Setting)
}