package com.example.mealplangenerator.data.model.mealplan

import com.example.mealplangenerator.data.model.mealplan.slot.Slot
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
        val lunchSlot = Slot(DayOfWeek.MONDAY, MealTime.LUNCH)
        mealPlan[lunchSlot] = null
        val dinnerSlot = Slot(DayOfWeek.SUNDAY, MealTime.DINNER)
        mealPlan[dinnerSlot] = null

        val mpc = MealPlanCriteria(mealPlan)
        assertTrue(mpc.containsKey(lunchSlot))
        assertTrue(mpc.containsKey(dinnerSlot))
    }
}