package com.example.mealplangenerator.data.model.meal

import com.example.mealplangenerator.data.model.mealplan.MealCriteria

data class MainDish(
    var name: String,
    var variation: List<String> = emptyList<String>(),
    var mealCriteria: MealCriteria = MealCriteria(),
    var maxOccurrenceByWeek: Int = 1,
    var isStaple: Boolean = false
)