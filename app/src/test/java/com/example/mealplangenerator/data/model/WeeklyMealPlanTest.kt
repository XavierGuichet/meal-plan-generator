package com.example.mealplangenerator.data.model

import com.example.mealplangenerator.data.model.meal.MainDish
import com.example.mealplangenerator.data.model.meal.Meal
import com.example.mealplangenerator.data.model.mealplan.MealPlan
import com.example.mealplangenerator.data.model.mealplan.MealSlot
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
    fun WeeklyMealPlan_Is_MealPlan()
    {
        assertTrue(wmp is MealPlan)
    }

    @Test
    fun WeeklyMealPlan_has_7_days_and_2_meal_per_day()
    {
        assertEquals(14,wmp.size)
        assertTrue(wmp.containsKey(MealSlot(DayOfWeek.MONDAY, MealTime.LUNCH)))
        assertTrue(wmp.containsKey(MealSlot(DayOfWeek.SUNDAY, MealTime.DINNER)))
    }
}