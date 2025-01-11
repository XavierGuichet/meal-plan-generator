package com.example.mealplangenerator.data.model.mealplan

data class PlanRules(var maxOccurrenceByWeek: Int = 1,
                var isStaple: Boolean = false)