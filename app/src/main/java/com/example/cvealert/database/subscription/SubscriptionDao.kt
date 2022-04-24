package com.example.cvealert.database.subscription

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubscriptionDao {

    @Insert
    fun insert(subscription: Subscription)

    @Update
    fun update(subscription: Subscription)

    @Delete
    fun delete(subscription: Subscription)

    @Query(
        "SELECT * FROM subscription " +
                "ORDER BY part, vendor, product, version, `update`, edition, language, sw_edition, " +
                "target_software, target_hardware, other, is_active, push_up_notification  DESC"
    )
    fun getAll(): LiveData<List<Subscription>>

    @Query("SELECT * FROM subscription WHERE is_active = 1")
    fun getAllActive(): List<Subscription>

    @Query("SELECT * FROM subscription WHERE id = :id")
    fun get(id: Int): LiveData<Subscription>
}