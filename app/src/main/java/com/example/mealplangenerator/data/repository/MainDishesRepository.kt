package com.example.mealplangenerator.data.repository

import com.example.mealplangenerator.data.model.meal.MainDish
import com.example.mealplangenerator.data.model.mealplan.MealCriteria
import com.example.mealplangenerator.data.model.mealplan.PlanRules
import com.example.mealplangenerator.enums.MealTime
import com.example.mealplangenerator.room.AppDatabase

open class MainDishesRepository(private val db: AppDatabase): MainDishesRepositoryInterface {
    private var allDishes = mutableListOf<MainDish>()
    private var initialized =  false

    private fun initAllDishes()
    {
        val dbDishes = db.dishDao()?.getAll()
        dbDishes?.forEach {
            val mainDish = MainDish(it.name, it.variations.split('|'), MealCriteria(it.mealTime,it.duration), PlanRules(it.maxOccurrenceByWeek, it.isStaple))
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

    override fun getStapleDishes(): List<MainDish> {
        if (!initialized)
            initAllDishes()
        return allDishes.filter { meal -> meal.planRules.isStaple }
    }

    private fun filterDishesByCriteria(validDishes: List<MainDish>, mealCriteria: MealCriteria): MutableList<MainDish> {
        var filteredDishes = validDishes
        if (mealCriteria.mealTime !== MealTime.ANY)
            filteredDishes = filteredDishes.filter { meal -> (meal.mealCriteria.mealTime == mealCriteria.mealTime || meal.mealCriteria.mealTime == MealTime.ANY) }

        filteredDishes = filteredDishes.filter { meal -> meal.mealCriteria.maxPreparationDuration <= mealCriteria.maxPreparationDuration }

        return filteredDishes.toMutableList()
    }
}