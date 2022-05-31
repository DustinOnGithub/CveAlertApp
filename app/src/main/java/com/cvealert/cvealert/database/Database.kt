package com.cvealert.cvealert.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cvealert.cvealert.database.cpe.Cpe
import com.cvealert.cvealert.database.cve.Cve
import com.cvealert.cvealert.database.cve.CveDao
import com.cvealert.cvealert.database.setting.Setting
import com.cvealert.cvealert.database.setting.SettingDao
import com.cvealert.cvealert.database.subscription.Subscription
import com.cvealert.cvealert.database.subscription.SubscriptionDao

@Database(
    version = 1,
    entities = [Setting::class, Subscription::class, Cve::class, Cpe::class],
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
                ).allowMainThreadQueries().build()

                INSTANCE = instance
                return instance
            }

        }
    }
}