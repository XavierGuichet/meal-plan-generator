package com.example.mealplangenerator.services

import com.example.mealplangenerator.data.model.meal.Meal
import com.example.mealplangenerator.data.model.mealplan.MealCriteria
import com.example.mealplangenerator.data.repository.MainDishesRepositoryInterface
import kotlin.random.Random

class MealFactory(private val mainDishRepo: MainDishesRepositoryInterface) {
    var random: Random = Random.Default

    fun getRandomMeal(mealCriteria: MealCriteria? = null): Meal? {
        val availableMainDish = mainDishRepo.getByCriteria(mealCriteria)
        if (availableMainDish.isEmpty())
            return null
        val mainDish = availableMainDish.random(random)
        return Meal(mainDish)
    }
}