package com.example.cvealert.data.setting

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cvealert.data.setting.Setting

@Dao
interface SettingDao {
    @Insert
    fun insert(setting: Setting)

    @Query("SELECT * FROM Setting ORDER BY id ASC")
    fun getAll(): LiveData<List<Setting>>

    @Query("SELECT * FROM Setting ORDER BY id ASC LIMIT 1 ")
    fun get(): LiveData<Setting>

    @Update
    fun updateSetting(setting: Setting)
}