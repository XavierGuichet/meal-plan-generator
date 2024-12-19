package com.example.mealplangenerator.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mealplangenerator.enums.Duration
import com.example.mealplangenerator.enums.MealTime

@Entity(tableName = "dishes")
class Dish(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "meal_time") val mealTime: MealTime,
    @ColumnInfo(name = "duration") val duration: Duration,
    @ColumnInfo(name = "max_occurrence") val maxOccurrenceByWeek: Int,
) {
}