package com.example.cvealert.data.Cve

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CveDao {

    @Insert
    fun insert(cve: Cve)

    @Update
    fun update(cve: Cve)

    @Delete
    fun delete(cve: Cve)

    @Query("SELECT * FROM cve")
    fun getAll(): LiveData<List<Cve>>
}