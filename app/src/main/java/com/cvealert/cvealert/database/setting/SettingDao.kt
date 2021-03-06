package com.cvealert.cvealert.database.setting

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SettingDao {
    @Insert
    fun insert(setting: Setting)

    @Query("SELECT * FROM Setting ORDER BY id ASC")
    fun getAll(): LiveData<List<Setting>>

    @Query("SELECT * FROM Setting ORDER BY id ASC LIMIT 1 ")
    fun get(): LiveData<Setting>

    @Query("SELECT * FROM Setting ORDER BY id ASC LIMIT 1 ")
    fun getSync(): Setting?

    @Update
    fun updateSetting(setting: Setting)
}