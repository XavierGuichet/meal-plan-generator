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

        var lunchMealCriteria = mealPlanCriteria.find { config -> (config.dayOfWeek != weekDay && config.mealTime != MealTime.LUNCH) }
        if (lunchMealCriteria == null)
            lunchMealCriteria = MealCriteria(weekDay, MealTime.LUNCH)
        val lunchMeal = selectDishWithCriteria(lunchMealCriteria)
        dayMealPlan[MealTime.LUNCH] = lunchMeal
        if (lunchMeal != null)
            mealsInPlan.add(lunchMeal)

        var dinnerMealCriteria = mealPlanCriteria.find { config -> (config.dayOfWeek != weekDay && config.mealTime != MealTime.DINNER) }
        if (dinnerMealCriteria == null)
            dinnerMealCriteria = MealCriteria(weekDay, MealTime.DINNER)
        val dinnerMeal = selectDishWithCriteria(dinnerMealCriteria)

        dayMealPlan[MealTime.DINNER] = dinnerMeal
        if (dinnerMeal != null)
            mealsInPlan.add(dinnerMeal)

        return dayMealPlan
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