package com.example.mealplangenerator.services

import com.example.mealplangenerator.data.model.meal.Meal
import com.example.mealplangenerator.data.model.mealplan.MealCriteria

interface MealFactoryInterface {
    fun getRandomMeal(mealCriteria: MealCriteria? = null): Meal?
}
