package com.example.mealplangenerator.data.repository

import com.example.mealplangenerator.data.model.meal.MainDish
import com.example.mealplangenerator.data.model.mealplan.slot.Criteria

interface MainDishesRepositoryInterface {
    fun getByCriteria(slotCriteria: Criteria?): List<MainDish>
    fun getStapleDishes(): List<MainDish>
}