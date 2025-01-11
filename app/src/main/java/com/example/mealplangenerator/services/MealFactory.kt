package com.example.mealplangenerator.services

import com.example.mealplangenerator.data.model.meal.Meal
import com.example.mealplangenerator.data.model.mealplan.slot.Criteria
import com.example.mealplangenerator.data.repository.MainDishesRepositoryInterface
import kotlin.random.Random

class MealFactory(private val mainDishRepo: MainDishesRepositoryInterface): MealFactoryInterface {
    var random: Random = Random.Default

    override fun getRandomMeal(slotCriteria: Criteria?): Meal? {
        val availableMainDish = mainDishRepo.getByCriteria(slotCriteria)
        if (availableMainDish.isEmpty())
            return null
        val mainDish = availableMainDish.random(random)
        return Meal(mainDish)
    }
}