package com.example.mealplangenerator.data.repository

import com.example.mealplangenerator.data.model.meal.MainDish
import com.example.mealplangenerator.data.model.mealplan.slot.Criteria
import com.example.mealplangenerator.data.model.mealplan.PlanRules
import com.example.mealplangenerator.enums.MealTime
import com.example.mealplangenerator.room.AppDatabase

open class MainDishesRepository(private val db: AppDatabase): MainDishesRepositoryInterface {
    private var allDishes = mutableListOf<MainDish>()
    private var initialized =  false

    private fun initAllDishes()
    {
        val dbDishes = db.mainDishDao()?.getAll()
        dbDishes?.forEach {
            val mainDish = MainDish(it.name, it.variations.split('|'), Criteria(it.mealTime,it.duration), PlanRules(it.maxOccurrenceByWeek, it.isStaple), it.isFullCourse)
            allDishes.add(mainDish)
        }
        initialized = true
    }

    override fun getByCriteria(slotCriteria: Criteria?): List<MainDish> {
        if (!initialized)
            initAllDishes()

        var validDishes = allDishes
        if (slotCriteria !== null)
            validDishes = filterDishesByCriteria(validDishes, slotCriteria)

        return validDishes
    }

    override fun getStapleDishes(): List<MainDish> {
        if (!initialized)
            initAllDishes()
        return allDishes.filter { meal -> meal.planRules.isStaple }
    }

    private fun filterDishesByCriteria(validDishes: List<MainDish>, slotCriteria: Criteria): MutableList<MainDish> {
        var filteredDishes = validDishes
        if (slotCriteria.mealTime !== MealTime.ANY)
            filteredDishes = filteredDishes.filter { meal -> (meal.slotCriteria.mealTime == slotCriteria.mealTime || meal.slotCriteria.mealTime == MealTime.ANY) }

        filteredDishes = filteredDishes.filter { meal -> meal.slotCriteria.maxPreparationDuration <= slotCriteria.maxPreparationDuration }

        return filteredDishes.toMutableList()
    }
}