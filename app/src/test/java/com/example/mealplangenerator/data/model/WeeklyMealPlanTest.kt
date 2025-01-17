package com.example.mealplangenerator.data.model

import com.example.mealplangenerator.data.model.mealplan.MealPlan
import com.example.mealplangenerator.data.model.mealplan.slot.Slot
import com.example.mealplangenerator.data.model.mealplan.WeeklyMealPlan
import com.example.mealplangenerator.enums.MealTime
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.time.DayOfWeek

class WeeklyMealPlanTest {
    private lateinit var wmp: WeeklyMealPlan

    @Before
    fun setUp()
    {
        wmp = WeeklyMealPlan()
    }

    @Test
    fun weeklyMealPlan_Is_MealPlan()
    {
        assertTrue(wmp is MealPlan)
    }

    @Test
    fun weeklyMealPlan_has_7_days_and_2_meal_per_day()
    {
        assertEquals(14,wmp.size)
        assertTrue(wmp.containsKey(Slot(DayOfWeek.MONDAY, MealTime.LUNCH)))
        assertTrue(wmp.containsKey(Slot(DayOfWeek.SUNDAY, MealTime.DINNER)))
    }
}