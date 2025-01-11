package com.example.mealplangenerator.data.model.mealplan.slot

import com.example.mealplangenerator.enums.MealTime
import java.time.DayOfWeek

data class Slot(val dayOfWeek: DayOfWeek, val mealTime: MealTime)