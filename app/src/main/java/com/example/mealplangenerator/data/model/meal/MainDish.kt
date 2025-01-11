package com.example.mealplangenerator.data.model.meal

import com.example.mealplangenerator.data.model.mealplan.MealCriteria
import com.example.mealplangenerator.data.model.mealplan.PlanRules

data class MainDish(
    var name: String,
    var variation: List<String> = emptyList<String>(),
    var mealCriteria: MealCriteria = MealCriteria(),
    var planRules: PlanRules = PlanRules()
)