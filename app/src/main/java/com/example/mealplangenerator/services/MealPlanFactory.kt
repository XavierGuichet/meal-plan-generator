package com.example.mealplangenerator.services

import com.example.mealplangenerator.data.model.meal.MainDish
import com.example.mealplangenerator.data.model.meal.Meal
import com.example.mealplangenerator.data.model.mealplan.slot.Criteria
import com.example.mealplangenerator.data.model.mealplan.MealPlanCriteria
import com.example.mealplangenerator.data.model.mealplan.slot.Slot
import com.example.mealplangenerator.data.model.mealplan.WeeklyMealPlan
import com.example.mealplangenerator.data.repository.MainDishesRepositoryInterface

class MealPlanFactory(private val mr: MainDishesRepositoryInterface) {
    private var mainDishesInPlan: MutableList<MainDish> = mutableListOf()

    fun makeWeeklyMealPlan(mealPlanCriteria: MealPlanCriteria): WeeklyMealPlan {
        var weeklyMealPlan = WeeklyMealPlan()
        weeklyMealPlan = addStapleMealsToPlan(weeklyMealPlan, mealPlanCriteria)

        weeklyMealPlan = fillPlanWithMeals(weeklyMealPlan, mealPlanCriteria)

        return weeklyMealPlan
    }

    private fun addStapleMealsToPlan(weeklyMealPlan: WeeklyMealPlan, mealPlanCriteria: MealPlanCriteria): WeeklyMealPlan {
        val stapleMainDishes = mr.getStapleDishes().shuffled()
        stapleMainDishes.forEach {
            val availableSlots = mealPlanCriteria.findSlotsWithCriteria(it.slotCriteria).shuffled().toMutableList()
            var validSlot: Slot? = null
            while (availableSlots.isNotEmpty() && validSlot == null) {
                val testSlot = availableSlots.removeAt(0)
                if (weeklyMealPlan[testSlot] == null) {
                    validSlot = testSlot
                    addMealToMealPlan(Meal(it), weeklyMealPlan, testSlot)
                }
            }
        }
        return weeklyMealPlan
    }

    private fun fillPlanWithMeals(weeklyMealPlan: WeeklyMealPlan, mealPlanCriteria: MealPlanCriteria): WeeklyMealPlan {
        weeklyMealPlan.forEach { (slot, meal) ->
            if (meal != null) return@forEach
            val slotCriteria = mealPlanCriteria[slot] ?: Criteria(slot.mealTime)
            makeMealForCriteria(slotCriteria)?.let { newMeal ->
                addMealToMealPlan(newMeal, weeklyMealPlan, slot)
            }
        }
        return weeklyMealPlan
    }

    private fun addMealToMealPlan(meal: Meal, weeklyMealPlan: WeeklyMealPlan, slot: Slot) {
        mainDishesInPlan.add(meal.mainDish)
        weeklyMealPlan[slot] = meal
    }

    private fun makeMealForCriteria(
        slotCriteria: Criteria
    ): Meal? {
        val mainDish = selectDishWithCriteria(slotCriteria)
        return mainDish?.let { Meal(it) }
    }

    private fun selectDishWithCriteria(slotCriteria: Criteria): MainDish? {
        val eligibleMeals = mr.getByCriteria(slotCriteria).toMutableList()
        var selectDish: MainDish? = null

        while (selectDish == null && eligibleMeals.isNotEmpty())
        {
            val tmpDish = eligibleMeals.random()
            eligibleMeals.remove(tmpDish)
            val isSameDish: (MainDish) -> Boolean = { it.name == tmpDish.name }
            val tooManyInMealPlan: Boolean = mainDishesInPlan.count(isSameDish) >= tmpDish.planRules.maxOccurrenceByWeek
            if (!tooManyInMealPlan)
                selectDish = tmpDish
        }

        return selectDish
    }
}