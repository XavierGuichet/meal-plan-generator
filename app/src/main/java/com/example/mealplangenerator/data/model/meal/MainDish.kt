package com.example.mealplangenerator.data.model.meal

import com.example.mealplangenerator.data.model.mealplan.slot.Criteria
import com.example.mealplangenerator.data.model.mealplan.PlanRules

data class MainDish(
    var name: String,
    var variation: List<String> = emptyList<String>(),
    var slotCriteria: Criteria = Criteria(),
    var planRules: PlanRules = PlanRules(),
    var isFullCourse: Boolean = false
)