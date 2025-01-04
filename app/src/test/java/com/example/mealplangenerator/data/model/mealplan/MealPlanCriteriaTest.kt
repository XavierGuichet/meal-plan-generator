package com.example.mealplangenerator.data.model.mealplan

import com.example.mealplangenerator.enums.MealTime
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.DayOfWeek

class MealPlanCriteriaTest {

    @Before
    fun setUp() {
    }

    @Test
    fun mealPlanCriteria_HasSameHashMap_AsAMealPlan()
    {
        val mealPlan = MealPlan()
        val mealSlot = MealSlot(DayOfWeek.MONDAY, MealTime.LUNCH)
        mealPlan[mealSlot] = null
        val mealSlot2 = MealSlot(DayOfWeek.SUNDAY, MealTime.DINNER)
        mealPlan[mealSlot2] = null

        val mpc = MealPlanCriteria(mealPlan)
        assertTrue(mpc.containsKey(mealSlot))
        assertTrue(mpc.containsKey(mealSlot2))
    }
}