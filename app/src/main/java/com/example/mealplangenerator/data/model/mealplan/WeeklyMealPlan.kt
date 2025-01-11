package com.example.mealplangenerator.data.model.mealplan

import com.example.mealplangenerator.data.model.mealplan.slot.Slot
import com.example.mealplangenerator.enums.MealTime
import java.time.DayOfWeek

class WeeklyMealPlan : MealPlan() {
    private val dailyMealTimes =  setOf(MealTime.LUNCH, MealTime.DINNER)

    init {
        initMapWithMealSlotForAWeek()
    }

    private fun initMapWithMealSlotForAWeek() {
        for (weekDay in enumValues<DayOfWeek>())
            dailyMealTimes.forEach { mt -> this[Slot(weekDay, mt)] = null }
    }
}