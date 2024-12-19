package com.example.mealplangenerator.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Dish::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dishDao(): DishDao?
}