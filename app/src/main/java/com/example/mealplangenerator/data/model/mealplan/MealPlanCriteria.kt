package com.example.mealplangenerator.data.model.mealplan

class MealPlanCriteria(mealPlan: MealPlan): HashMap<MealSlot, MealCriteria?>() {
    init {
        mealPlan.forEach {
            this[it.key] = MealCriteria(it.key.mealTime)
        }
    }
}