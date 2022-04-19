package com.example.cvealert.data.subscription

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cvealert.data.subscription.Subscription

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

    @Query("SELECT * FROM subscription WHERE id = :id")
    fun get(id: Int): LiveData<Subscription>
}