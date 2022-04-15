package com.example.cvealert.data

import android.content.Context
import android.os.strictmode.InstanceCountViolation
import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [Setting::class, Subscription::class], exportSchema = false)
abstract class MyDatabase : RoomDatabase() {

    abstract fun settingDao(): SettingDao
    abstract fun subscriptionDao(): SubscriptionDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "my_database"
                ).build()

                INSTANCE = instance
                return instance
            }

        }
    }
}