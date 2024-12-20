package com.example.mealplangenerator.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mealplangenerator.enums.Duration
import com.example.mealplangenerator.enums.MealTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [Dish::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dishDao(): DishDao?

    private class AppDatabaseCallback(private val scope: CoroutineScope): Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val dishDao = database.dishDao()
                    dishDao?.deleteAll()

                    dishDao?.insert(Dish("Cordon bleu", MealTime.LUNCH, Duration.QUICK, 2))
                    dishDao?.insert(Dish("Pavé de saumon", MealTime.LUNCH, Duration.QUICK))
                    dishDao?.insert(Dish("Cotes de porc citron", MealTime.LUNCH, Duration.SHORT))
                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mpg_db"
                ).addCallback(AppDatabaseCallback(scope))
                .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}