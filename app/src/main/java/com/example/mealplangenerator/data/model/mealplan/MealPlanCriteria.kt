package com.example.mealplangenerator.data.model.mealplan

import com.example.mealplangenerator.enums.Duration
import com.example.mealplangenerator.enums.MealTime

class MealPlanCriteria(mealPlan: MealPlan): HashMap<MealSlot, MealCriteria>() {
    init {
        mealPlan.forEach {
            this[it.key] = MealCriteria(it.key.mealTime)
        }
    }

    fun setDefaultPrepDurationFor(mealTime: MealTime, duration: Duration)
    {
        this.forEach { (slot, criteria) ->
            if (slot.mealTime == mealTime) {
                criteria.setDuration(duration)
            }
        }
    }

    fun findSlotsWithCriteria(requestCriteria: MealCriteria): Set<MealSlot> {
        val res = this.filter { (slot, mealCriteria) ->
            (requestCriteria.mealTime == MealTime.ANY || requestCriteria.mealTime == mealCriteria.mealTime)
        }
        return res.keys
    }
}