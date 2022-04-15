package com.example.cvealert.data

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

    @Query("SELECT * FROM subscription ORDER BY vendor, product DESC")
    fun getAll(): LiveData<List<Subscription>>

    @Query("SELECT * FROM subscription WHERE id = :id")
    fun get(id: Int): LiveData<Subscription>
}