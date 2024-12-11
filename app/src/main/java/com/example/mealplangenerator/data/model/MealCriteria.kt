package com.example.mealplangenerator.data.model

import com.example.mealplangenerator.enums.Duration
import com.example.mealplangenerator.enums.MealTime
import java.time.DayOfWeek

class MealCriteria(val dayOfWeek: DayOfWeek, val mealTime: MealTime = MealTime.ANY, val maxPreparationDuration: Duration = Duration.SUPER) {
}