package com.example.mealplangenerator.data.model.mealplan

import com.example.mealplangenerator.data.model.meal.Meal
import com.example.mealplangenerator.data.model.mealplan.slot.Slot
import com.example.mealplangenerator.enums.MealTime
import java.time.DayOfWeek
import java.util.SortedMap

open class MealPlan : HashMap<Slot, Meal?>() {
    fun getSortedBySlot(): SortedMap<Slot, Meal?> {
        return this.toSortedMap(compareBy<Slot>{ it.dayOfWeek }.thenBy { it.mealTime })
    }

    fun getByDay(dayOfWeek: DayOfWeek): HashMap<MealTime, Meal?> {
        val sortedByTimeOfDayOfWeek =  this.filterKeys { it.dayOfWeek == dayOfWeek }.toSortedMap(compareBy<Slot>{ it.mealTime })
        return sortedByTimeOfDayOfWeek.map { (slot, value) -> slot.mealTime to value }.toMap(HashMap<MealTime, Meal?>())
    }

    fun getSortedDays(): List<DayOfWeek> {
        return this.getSortedBySlot().map { (key, meal) -> key.dayOfWeek }.distinct()
    }
}
