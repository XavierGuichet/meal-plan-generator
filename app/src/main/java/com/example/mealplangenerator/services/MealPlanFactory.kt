package com.example.mealplangenerator.services

import com.example.mealplangenerator.data.model.MainDish
import com.example.mealplangenerator.data.model.MealCriteria
import com.example.mealplangenerator.data.model.WeeklyMealPlan
import com.example.mealplangenerator.data.repository.MainDishesRepository
import com.example.mealplangenerator.enums.Duration
import com.example.mealplangenerator.enums.MealTime
import java.time.DayOfWeek

class MealPlanFactory(private val mr: MainDishesRepository) {
    private val dailyMealTimes =  setOf(MealTime.LUNCH, MealTime.DINNER)

    private var mealsInPlan: MutableList<MainDish> = mutableListOf()

    fun makePlanForOneWeek(mealPlanCriteria: Set<MealCriteria>): WeeklyMealPlan {
        var weeklyMealPlan = WeeklyMealPlan()
        weeklyMealPlan = addStapleMealsToPlan(weeklyMealPlan, mealPlanCriteria)

        overridePlanWithDailyPlan(weeklyMealPlan, mealPlanCriteria)

        return weeklyMealPlan
    }

    private fun addStapleMealsToPlan(weeklyMealPlan: WeeklyMealPlan, mealPlanCriteria: Set<MealCriteria>): WeeklyMealPlan {
        val stapleCriterion = MealCriteria(DayOfWeek.SUNDAY, MealTime.ANY, Duration.QUICK, true)
        val stapleMeals = mr.getByCriteria(stapleCriterion).toMutableList()

        data class Slot(val dayOfWeek: DayOfWeek, val mealTime: MealTime)
        var availableSlot: MutableList<Slot>

        stapleMeals.forEach {
            availableSlot = mutableListOf<Slot>()

            mealPlanCriteria.forEach { mpc ->
                if (it.mealTime == mpc.mealTime && it.preparationDuration <= mpc.maxPreparationDuration) {
                    val slot = Slot(mpc.dayOfWeek, mpc.mealTime)
                    availableSlot.add(slot)
                }
            }

            val winningSlot = availableSlot.random()
            weeklyMealPlan.addMealToSlot(it, winningSlot.dayOfWeek, winningSlot.mealTime)

        }
        return weeklyMealPlan;
    }

    private fun overridePlanWithDailyPlan(weeklyMealPlan: WeeklyMealPlan, mealPlanCriteria: Set<MealCriteria>) {
        weeklyMealPlan.mealPlan.entries.forEach { it ->
            weeklyMealPlan.mealPlan[it.key] = makePlanForOneDay(mealPlanCriteria, it.key)
        }
    }

    private fun makePlanForOneDay(mealPlanCriteria: Set<MealCriteria>, weekDay: DayOfWeek): HashMap<MealTime, MainDish?> {
        val dayMealPlan = HashMap<MealTime, MainDish?>(2)
        for (dailyMealTime in dailyMealTimes) {
            val meal = getOneMealForCriteria(mealPlanCriteria, weekDay, dailyMealTime)
            dayMealPlan[dailyMealTime] = meal
            if (meal != null)
                mealsInPlan.add(meal)
        }

        return dayMealPlan
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