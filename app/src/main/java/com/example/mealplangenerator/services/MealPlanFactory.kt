package com.example.mealplangenerator.services

import com.example.mealplangenerator.data.model.MainDish
import com.example.mealplangenerator.data.model.MealCriteria
import com.example.mealplangenerator.data.model.MealPlan
import com.example.mealplangenerator.data.model.MealSlot
import com.example.mealplangenerator.data.model.WeeklyMealPlan
import com.example.mealplangenerator.data.repository.MainDishesRepositoryInterface
import com.example.mealplangenerator.enums.Duration
import com.example.mealplangenerator.enums.MealTime
import java.time.DayOfWeek

class MealPlanFactory(private val mr: MainDishesRepositoryInterface) {
    private val dailyMealTimes =  setOf(MealTime.LUNCH, MealTime.DINNER)
    private var mealsInPlan: MutableList<MainDish> = mutableListOf()

    fun makeMealPlan(): MealPlan
    {
        return MealPlan()
    }

    fun makePlanForOneWeek(mealPlanCriteria: Set<MealCriteria>): WeeklyMealPlan {
        var weeklyMealPlan = WeeklyMealPlan()
        weeklyMealPlan = addStapleMealsToPlan(weeklyMealPlan, mealPlanCriteria)

        weeklyMealPlan = fillPlanWithMeals(weeklyMealPlan, mealPlanCriteria)

        return weeklyMealPlan
    }

    private fun addStapleMealsToPlan(weeklyMealPlan: WeeklyMealPlan, mealPlanCriteria: Set<MealCriteria>): WeeklyMealPlan {
        val stapleMeals = getStapleMeals()
        // FIXME : a stapleMeal can override another stapleMeal
        stapleMeals.forEach {
            val winningSlot = getRandomAvailableSlot(mealPlanCriteria, it)
            weeklyMealPlan.addMealToSlot(it, winningSlot.dayOfWeek, winningSlot.mealTime)
        }
        return weeklyMealPlan
    }

    private fun getStapleMeals(): List<MainDish> {
        val stapleCriterion = MealCriteria(DayOfWeek.SUNDAY, MealTime.ANY, Duration.QUICK, true)
        return mr.getByCriteria(stapleCriterion)
    }

    private fun getRandomAvailableSlot(mealPlanCriteria: Set<MealCriteria>,it: MainDish): MealSlot {
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
        weeklyMealPlan.mealPlan.entries.forEach { wmp ->
            for (dailyMealTime in dailyMealTimes) {
                if (weeklyMealPlan.getMealAtSlot(wmp.key, dailyMealTime) != null)
                    continue
                val meal = getOneMealForCriteria(mealPlanCriteria, wmp.key, dailyMealTime)
                if (meal != null) {
                    weeklyMealPlan.addMealToSlot(meal, wmp.key, dailyMealTime)
                    mealsInPlan.add(meal)
                }
            }
        }

        return weeklyMealPlan
    }

    private fun getOneMealForCriteria(
        mealPlanCriteria: Set<MealCriteria>,
        weekDay: DayOfWeek, mealTime: MealTime,
    ):MainDish? {
        val lunchMealCriteria = findMealCriteria(mealPlanCriteria, weekDay, mealTime)
        return selectDishWithCriteria(lunchMealCriteria)
    }

    private fun findMealCriteria(mealPlanCriteria: Set<MealCriteria>, weekDay: DayOfWeek,mealTime: MealTime): MealCriteria {
        var criteria = mealPlanCriteria.find { config -> (config.dayOfWeek == weekDay && config.mealTime == mealTime) }
        if (criteria == null)
            criteria = MealCriteria(weekDay, mealTime)
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
            val tooManyInMealPlan: Boolean = mealsInPlan.count(isSameDish) >= tmpDish.maxOccurrenceByWeek
            if (!tooManyInMealPlan)
                selectDish = tmpDish
        }

        return selectDish
    }
}