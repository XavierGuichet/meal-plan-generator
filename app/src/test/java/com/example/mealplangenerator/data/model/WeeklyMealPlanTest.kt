package com.example.mealplangenerator.data.model

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

    @Test
    fun canAddAndRetrieveMeal_WithLegacyMethod()
    {
        wmp.addMealToSlot(MainDish("My dish") ,DayOfWeek.MONDAY, MealTime.LUNCH)

        val myDish = wmp.getMealAtSlot(DayOfWeek.MONDAY, MealTime.LUNCH);
        assertEquals("My dish", myDish?.name)
    }

    @Test
    fun getLegacyWmpFormat()
    {
        wmp.addMealToSlot(MainDish("My dish 1") ,DayOfWeek.MONDAY, MealTime.LUNCH)
        wmp.addMealToSlot(MainDish("My dish 2") ,DayOfWeek.WEDNESDAY, MealTime.DINNER)

        val legacyWMPMealPlan = wmp.mealPlan

        assertTrue(legacyWMPMealPlan.containsKey(DayOfWeek.MONDAY))
        assertTrue(legacyWMPMealPlan[DayOfWeek.MONDAY]?.containsKey(MealTime.LUNCH) ?: false)
        assertEquals("My dish 1", legacyWMPMealPlan[DayOfWeek.MONDAY]?.get(MealTime.LUNCH)?.name ?: "Name Not Found")
        assertTrue(legacyWMPMealPlan.contains(DayOfWeek.WEDNESDAY))
        assertTrue(legacyWMPMealPlan[DayOfWeek.WEDNESDAY]?.containsKey(MealTime.DINNER) ?: false)
        assertEquals("My dish 2", legacyWMPMealPlan[DayOfWeek.WEDNESDAY]?.get(MealTime.DINNER)?.name ?: "Name Not Found")
    }



}