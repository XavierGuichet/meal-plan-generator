package com.example.mealplangenerator.services

import com.example.mealplangenerator.data.model.meal.Meal
import com.example.mealplangenerator.data.model.mealplan.slot.Criteria

interface MealFactoryInterface {
    fun getRandomMeal(slotCriteria: Criteria? = null): Meal?
}
