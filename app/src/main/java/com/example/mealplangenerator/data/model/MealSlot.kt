package com.example.mealplangenerator.data.model

import com.example.mealplangenerator.enums.MealTime
import java.time.DayOfWeek

data class MealSlot(val dayOfWeek: DayOfWeek, val mealTime: MealTime)