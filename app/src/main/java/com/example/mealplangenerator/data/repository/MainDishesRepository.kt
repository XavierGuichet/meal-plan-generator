package com.example.mealplangenerator.data.repository

import com.example.mealplangenerator.data.model.MainDish
import com.example.mealplangenerator.data.model.MealCriteria
import com.example.mealplangenerator.enums.MealTime
import com.example.mealplangenerator.room.AppDatabase

open class MainDishesRepository(private val db: AppDatabase): MainDishesRepositoryInterface {
    private var allDishes = mutableListOf<MainDish>()
    private var initialized =  false

    private fun initAllDishes()
    {
        val dbDishes = db.dishDao()?.getAll()
        dbDishes?.forEach {
            val mainDish = MainDish(it.name,it.mealTime,it.duration,it.maxOccurrenceByWeek, it.variations.split('|'), it.isStaple)
            allDishes.add(mainDish)
        }
        initialized = true
    }

    override fun getByCriteria(mealCriteria: MealCriteria?): List<MainDish> {
        if (!initialized)
            initAllDishes()

        var validDishes = allDishes
        if (mealCriteria !== null)
            validDishes = filterDishesByCriteria(validDishes, mealCriteria)

        return validDishes
    }

    private fun filterDishesByCriteria(validDishes: List<MainDish>, mealCriteria: MealCriteria ): MutableList<MainDish> {
        var filteredDishes =validDishes
        if (mealCriteria.mealTime !== MealTime.ANY)
            filteredDishes = filteredDishes.filter { meal -> (meal.mealTime == mealCriteria.mealTime || meal.mealTime == MealTime.ANY) }

        filteredDishes = filteredDishes.filter { meal -> meal.preparationDuration <= mealCriteria.maxPreparationDuration }

        filteredDishes = filteredDishes.filter { meal -> meal.isStaple == mealCriteria.isStaple }

        return filteredDishes.toMutableList()
    }
}