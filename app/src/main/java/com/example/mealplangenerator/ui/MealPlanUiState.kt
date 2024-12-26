package com.example.mealplangenerator.ui

import com.example.mealplangenerator.data.model.WeeklyMealPlan

data class MealPlanUiState(
    val weeklyMealPlan: WeeklyMealPlan = WeeklyMealPlan()
)