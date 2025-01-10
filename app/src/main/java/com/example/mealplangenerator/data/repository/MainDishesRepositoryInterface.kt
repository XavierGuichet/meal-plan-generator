package com.example.mealplangenerator.data.repository

import com.example.mealplangenerator.data.model.meal.MainDish
import com.example.mealplangenerator.data.model.mealplan.MealCriteria

interface MainDishesRepositoryInterface {
    fun getByCriteria(mealCriteria: MealCriteria?): List<MainDish>
    fun getStapleDishes(): List<MainDish>
}