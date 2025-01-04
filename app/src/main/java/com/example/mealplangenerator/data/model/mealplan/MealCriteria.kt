package com.example.mealplangenerator.data.model.mealplan

import com.example.mealplangenerator.enums.Duration
import com.example.mealplangenerator.enums.MealTime

class MealCriteria(
    val mealTime: MealTime = MealTime.ANY,
    val maxPreparationDuration: Duration = Duration.SUPER,
    val isStaple: Boolean = false
)