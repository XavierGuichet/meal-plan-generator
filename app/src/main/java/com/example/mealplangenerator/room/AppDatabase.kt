package com.example.mealplangenerator.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Dish::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dishDao(): DishDao
}