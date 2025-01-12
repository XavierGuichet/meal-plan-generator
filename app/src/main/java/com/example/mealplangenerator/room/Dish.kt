package com.example.mealplangenerator.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mealplangenerator.enums.Duration
import com.example.mealplangenerator.enums.MealTime

@Entity(tableName = "dishes")
class Dish(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "meal_time") val mealTime: MealTime,
    @ColumnInfo(name = "duration") val duration: Duration,
    @ColumnInfo(name = "max_occurrence") val maxOccurrenceByWeek: Int = 1,
    @ColumnInfo(name = "variations") val variations: String = "",
    @ColumnInfo(name = "is_staple") val isStaple: Boolean =  false,
    @ColumnInfo(name = "is_full_course") val isFullCourse: Boolean =  false
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}