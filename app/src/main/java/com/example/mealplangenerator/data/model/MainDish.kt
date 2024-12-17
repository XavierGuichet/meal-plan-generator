package com.example.mealplangenerator.data.model

import com.example.mealplangenerator.enums.Duration
import com.example.mealplangenerator.enums.MealTime

data class MainDish(
    var name: String,
    var mealTime: MealTime = MealTime.ANY,
    var preparationDuration: Duration = Duration.LONG,
    var maxOccurrenceByWeek: Int = 1,
    var variation: Array<String> = emptyArray<String>()
)