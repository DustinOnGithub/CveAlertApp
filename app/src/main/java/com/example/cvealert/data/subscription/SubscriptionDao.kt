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

    @Query("SELECT * FROM subscription ORDER BY vendor, product DESC")
    fun getAll(): LiveData<List<Subscription>>

    @Query("SELECT * FROM subscription WHERE id = :id")
    fun get(id: Int): LiveData<Subscription>
}