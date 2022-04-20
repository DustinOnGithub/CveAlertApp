package com.example.cvealert.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cvealert.data.Cve.Cve
import com.example.cvealert.data.Cve.CveDao
import com.example.cvealert.data.setting.Setting
import com.example.cvealert.data.setting.SettingDao
import com.example.cvealert.data.subscription.Subscription
import com.example.cvealert.data.subscription.SubscriptionDao

@Database(
    version = 1,
    entities = [Setting::class, Subscription::class, Cve::class],
    exportSchema = false
)
abstract class MyDatabase : RoomDatabase() {

    abstract fun settingDao(): SettingDao
    abstract fun subscriptionDao(): SubscriptionDao
    abstract fun cveDao(): CveDao

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