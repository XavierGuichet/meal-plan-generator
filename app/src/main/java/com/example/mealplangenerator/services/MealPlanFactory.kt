package com.example.mealplangenerator.services

import com.example.mealplangenerator.data.model.MainDish
import com.example.mealplangenerator.data.model.MealCriteria
import com.example.mealplangenerator.data.repository.MainDishesRepository
import com.example.mealplangenerator.enums.MealTime
import java.time.DayOfWeek

class MealPlanFactory {
    private val weekDays = setOf(
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY,
        DayOfWeek.SATURDAY,
        DayOfWeek.SUNDAY,
    )

    private val dailyMealTimes =  setOf(MealTime.LUNCH, MealTime.DINNER)

    private var mealsInPlan: MutableList<MainDish> = mutableListOf()
    private val mr = MainDishesRepository()

    fun makePlanForOneWeek(mealPlanCriteria: Set<MealCriteria>): HashMap<DayOfWeek, HashMap<MealTime, MainDish?>> {
        val mealPlan = HashMap<DayOfWeek, HashMap<MealTime, MainDish?>>(7)
        for (weekDay in weekDays)
            mealPlan[weekDay] = makePlanForOneDay(mealPlanCriteria, weekDay)

        return mealPlan
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
            val alreadyInMealPlan = mealsInPlan.any(isSameDish)
            if (!alreadyInMealPlan)
                selectDish = tmpDish
        }

        return selectDish
    }
}