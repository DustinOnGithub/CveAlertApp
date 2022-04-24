package com.example.cvealert.database.cve

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cvealert.database.cpe.Cpe

@Dao
interface CveDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCVE(cve: Cve)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCVEs(cves: Iterable<Cve>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCVEsSync(cves: Iterable<Cve>)

    @Insert
    fun insertCPE(cpe: Cpe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCPEs(cpes: Iterable<Cpe>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCPEsSync(cpes: Iterable<Cpe>)

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