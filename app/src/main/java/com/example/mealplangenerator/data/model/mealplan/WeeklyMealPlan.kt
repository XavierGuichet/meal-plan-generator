package com.example.mealplangenerator.data.model.mealplan

import com.example.mealplangenerator.enums.MealTime
import java.time.DayOfWeek

class WeeklyMealPlan : MealPlan() {
    private val dailyMealTimes =  setOf(MealTime.LUNCH, MealTime.DINNER)

    init {
        initMapWithMealSlotForAWeek()
    }

    private fun initMapWithMealSlotForAWeek() {
        for (weekDay in enumValues<DayOfWeek>())
            dailyMealTimes.forEach { mt -> this[MealSlot(weekDay, mt)] = null }
    }
}