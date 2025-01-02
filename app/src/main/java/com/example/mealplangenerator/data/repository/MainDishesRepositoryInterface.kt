package com.example.mealplangenerator.data.repository

import com.example.mealplangenerator.data.model.MainDish
import com.example.mealplangenerator.data.model.MealCriteria

interface MainDishesRepositoryInterface {
    fun getByCriteria(mealCriteria: MealCriteria?): List<MainDish>
}