package com.example.mealplangenerator.data.model

import com.example.mealplangenerator.enums.MealTime
import java.time.DayOfWeek

class WeeklyMealPlan : MealPlan() {
    private val dailyMealTimes =  setOf(MealTime.LUNCH, MealTime.DINNER)
    val mealPlan: HashMap<DayOfWeek, HashMap<MealTime, MainDish?>>
        get() = getLegacyWMPFormat()

    init {
        initMapWithMealSlotForAWeek()
    }

    private fun initMapWithMealSlotForAWeek() {
        for (weekDay in enumValues<DayOfWeek>())
            dailyMealTimes.forEach { mt -> this[MealSlot(weekDay, mt)] = null }
    }

    @Deprecated("Could use the hashMap directly, getSortedBySlot() or getByDay()")
    fun getLegacyWMPFormat(): HashMap<DayOfWeek, HashMap<MealTime, MainDish?>>
    {
        val mealPlan: HashMap<DayOfWeek, HashMap<MealTime, MainDish?>> = HashMap(7)
        val plan = this.getSortedBySlot()
        plan.forEach{
            val key = it.key
            val meal = it.value
            if (!mealPlan.contains(key.dayOfWeek))
                mealPlan[key.dayOfWeek] = HashMap(2)
            mealPlan[key.dayOfWeek]?.set(key.mealTime, meal?.mainDish)
        }

        return mealPlan
    }

    @Deprecated("use WeeklyMealPlan[MealSlot] = Meal")
    fun addMealToSlot(dish: MainDish, dayOfWeek: DayOfWeek, mealTime: MealTime)
    {
        val mealSlot = MealSlot(dayOfWeek, mealTime)
        this[mealSlot] = Meal(dish)
    }

    @Deprecated("use WeeklyMealPlan[MealSlot]")
    fun getMealAtSlot(dayOfWeek: DayOfWeek, mealTime: MealTime): MainDish? {
        val mealSlot = MealSlot(dayOfWeek, mealTime)
        val meal = this[mealSlot]
        return meal?.mainDish
    }


}