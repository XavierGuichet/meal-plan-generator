package com.example.mealplangenerator.data.model

import com.example.mealplangenerator.enums.MealTime
import java.time.DayOfWeek

class WeeklyMealPlan() {
    val mealPlan: HashMap<DayOfWeek, HashMap<MealTime, MainDish?>> = HashMap<DayOfWeek, HashMap<MealTime, MainDish?>>(7)

    init {
        for (weekDay in enumValues<DayOfWeek>()) {
            mealPlan[weekDay] = HashMap<MealTime, MainDish?>(2)
        }
    }
}