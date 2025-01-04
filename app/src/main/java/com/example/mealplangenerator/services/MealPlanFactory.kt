package com.example.mealplangenerator.services

import com.example.mealplangenerator.data.model.meal.MainDish
import com.example.mealplangenerator.data.model.meal.Meal
import com.example.mealplangenerator.data.model.mealplan.MealCriteria
import com.example.mealplangenerator.data.model.mealplan.MealPlan
import com.example.mealplangenerator.data.model.mealplan.MealSlot
import com.example.mealplangenerator.data.model.mealplan.WeeklyMealPlan
import com.example.mealplangenerator.data.repository.MainDishesRepositoryInterface
import com.example.mealplangenerator.enums.Duration
import com.example.mealplangenerator.enums.MealTime
import java.time.DayOfWeek

class MealPlanFactory(private val mr: MainDishesRepositoryInterface) {
    private var mainDishesInPlan: MutableList<MainDish> = mutableListOf()

    fun makeWeeklyMealPlan(mealPlanCriteria: Set<MealCriteria>): WeeklyMealPlan {
        var weeklyMealPlan = WeeklyMealPlan()
        weeklyMealPlan = addStapleMealsToPlan(weeklyMealPlan, mealPlanCriteria)

        weeklyMealPlan = fillPlanWithMeals(weeklyMealPlan, mealPlanCriteria)

        return weeklyMealPlan
    }

    private fun addStapleMealsToPlan(weeklyMealPlan: WeeklyMealPlan, mealPlanCriteria: Set<MealCriteria>): WeeklyMealPlan {
        val stapleMainDishes = getStapleMainDishes()
        // FIXME : a stapleMeal can override another stapleMeal
        stapleMainDishes.forEach {
            val winningSlot = getRandomAvailableSlot(mealPlanCriteria, it)
            addMealToMealPlan(Meal(it), weeklyMealPlan, winningSlot)
        }
        return weeklyMealPlan
    }

    private fun getStapleMainDishes(): List<MainDish> {
        val stapleCriterion = MealCriteria(DayOfWeek.SUNDAY, MealTime.ANY, Duration.QUICK, true)
        return mr.getByCriteria(stapleCriterion)
    }

    private fun getRandomAvailableSlot(mealPlanCriteria: Set<MealCriteria>, it: MainDish): MealSlot {
        val availableSlot = getAvailableMealSlots(mealPlanCriteria, it.mealTime, it.preparationDuration)
        return availableSlot.random()
    }

    private fun getAvailableMealSlots(mealPlanCriteria: Set<MealCriteria>, mealTime: MealTime, duration: Duration): List<MealSlot> {
        val availableMpc = mealPlanCriteria.filter { mpc -> mealTime == mpc.mealTime && duration <= mpc.maxPreparationDuration }
        val availableSlot: List<MealSlot> = availableMpc.map { mpc ->
            MealSlot(mpc.dayOfWeek, mpc.mealTime)
        }

        return availableSlot
    }

    private fun fillPlanWithMeals(weeklyMealPlan: WeeklyMealPlan, mealPlanCriteria: Set<MealCriteria>): WeeklyMealPlan {
        weeklyMealPlan.forEach { (slot, meal) ->
            if (meal != null) return@forEach
            val mealCriteria = findMealCriteria(mealPlanCriteria, slot)
            makeMealForCriteria(mealCriteria)?.let { newMeal ->
                addMealToMealPlan(newMeal, weeklyMealPlan, slot)
            }
        }
        return weeklyMealPlan
    }

    private fun addMealToMealPlan(meal: Meal, weeklyMealPlan: WeeklyMealPlan, slot: MealSlot) {
        mainDishesInPlan.add(meal.mainDish)
        weeklyMealPlan[slot] = meal
    }

    private fun makeMealForCriteria(
        mealCriteria: MealCriteria
    ): Meal? {
        val mainDish = selectDishWithCriteria(mealCriteria)
        return mainDish?.let { Meal(it) }
    }

    private fun findMealCriteria(mealPlanCriteria: Set<MealCriteria>, mealSlot: MealSlot): MealCriteria {
        var criteria = mealPlanCriteria.find { config -> (config.dayOfWeek == mealSlot.dayOfWeek && config.mealTime == mealSlot.mealTime) }
        if (criteria == null)
            criteria = MealCriteria(mealSlot.dayOfWeek, mealSlot.mealTime)
        return criteria
    }

    private fun selectDishWithCriteria(mealCriteria: MealCriteria): MainDish? {
        val eligibleMeals = mr.getByCriteria(mealCriteria).toMutableList()
        var selectDish: MainDish? = null

        while (selectDish == null && eligibleMeals.isNotEmpty())
        {
            val tmpDish = eligibleMeals.random()
            eligibleMeals.remove(tmpDish)
            val isSameDish: (MainDish) -> Boolean = { it.name == tmpDish.name }
            val tooManyInMealPlan: Boolean = mainDishesInPlan.count(isSameDish) >= tmpDish.maxOccurrenceByWeek
            if (!tooManyInMealPlan)
                selectDish = tmpDish
        }

        return selectDish
    }
}