package com.example.mealplangenerator.data.repository

import com.example.mealplangenerator.data.model.MainDish
import com.example.mealplangenerator.data.model.MealCriteria
import com.example.mealplangenerator.enums.MealTime
import com.example.mealplangenerator.room.AppDatabase

class MainDishesRepository(private val db: AppDatabase) {
    private var allDishes = mutableListOf<MainDish>()
    private var initialized =  false

    private fun initAllDishes()
    {
        val dbDishes = db.dishDao()?.getAll()
        dbDishes?.forEach {
            val mainDish = MainDish(it.name,it.mealTime,it.duration,it.maxOccurrenceByWeek)
            allDishes.add(mainDish)
        }
    }

    fun getByCriteria(mealCriteria: MealCriteria?): List<MainDish> {
        if (!initialized)
            initAllDishes()

        var validDishes = allDishes
        if (mealCriteria !== null)
            validDishes = filterDishesByCriteria(validDishes, mealCriteria)

        return validDishes
    }

    private fun filterDishesByCriteria(validDishes: List<MainDish>, mealCriteria: MealCriteria ): MutableList<MainDish> {
        var filteredDishes = validDishes.filter { meal -> (meal.mealTime == mealCriteria.mealTime || meal.mealTime == MealTime.ANY) }
        filteredDishes = filteredDishes.filter { meal -> meal.preparationDuration <= mealCriteria.maxPreparationDuration }
        return filteredDishes.toMutableList()
    }
}