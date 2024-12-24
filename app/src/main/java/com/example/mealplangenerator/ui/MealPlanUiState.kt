package com.example.mealplangenerator.ui

import com.example.mealplangenerator.data.model.MainDish
import com.example.mealplangenerator.enums.MealTime
import java.time.DayOfWeek

data class MealPlanUiState(
    val mealPlan: HashMap<DayOfWeek, HashMap<MealTime, MainDish?>> = HashMap()
)