package com.example.mealplangenerator.data.model.mealplan.slot

import com.example.mealplangenerator.enums.Duration
import com.example.mealplangenerator.enums.MealTime

class Criteria(
    val mealTime: MealTime = MealTime.ANY,
    var maxPreparationDuration: Duration = Duration.SUPER
)