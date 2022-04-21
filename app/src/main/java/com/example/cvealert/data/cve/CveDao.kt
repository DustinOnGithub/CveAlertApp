package com.example.cvealert.data.cve

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cvealert.data.cpe.Cpe

@Dao
interface CveDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cve: Cve)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMultiple(cves: Iterable<Cve>)

    @Insert
    fun insert(cpe: Cpe)

    @Update
    fun update(cve: Cve)

    @Delete
    fun delete(cve: Cve)

    @Query("SELECT * FROM cve")
    fun getAll(): LiveData<List<Cve>>

    @Query(
        "SELECT * " +
                "FROM cve " +
                "inner join cpe on cpe.cve = cve.cve " +
                "WHERE cpe.string = :cpeString"
    )
    fun getAllWithCpe(cpeString: String): LiveData<List<Cve>>
}