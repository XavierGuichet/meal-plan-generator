package com.example.mealplangenerator.services

import com.example.mealplangenerator.data.model.MainDish
import com.example.mealplangenerator.data.model.MealCriteria
import com.example.mealplangenerator.data.repository.MainDishesRepository
import com.example.mealplangenerator.enums.MealTime
import java.time.DayOfWeek

class MealPlanFactory {
    val weekDays = setOf(
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY,
        DayOfWeek.SATURDAY,
        DayOfWeek.SUNDAY,
    )

    fun create(mealPlanCriteria: Set<MealCriteria>): HashMap<DayOfWeek, HashMap<MealTime, MainDish>> {
        val mr = MainDishesRepository()
        val mealPlan = HashMap<DayOfWeek, HashMap<MealTime, MainDish>>(7)

        for (weekDay in weekDays)
        {
            val lunchMealCriteria = mealPlanCriteria.find { config -> (config.dayOfWeek != weekDay && config.mealTime != MealTime.LUNCH)}
            val dinnerMealCriteria = mealPlanCriteria.find { config -> (config.dayOfWeek != weekDay && config.mealTime != MealTime.DINNER)}

            val dayMealPlan = HashMap<MealTime, MainDish>(2)
            dayMealPlan[MealTime.LUNCH] = mr.getOneForCriteria(lunchMealCriteria)
            dayMealPlan[MealTime.DINNER] = mr.getOneForCriteria(dinnerMealCriteria)
            mealPlan[weekDay] = dayMealPlan
        }

        return mealPlan
    }

}