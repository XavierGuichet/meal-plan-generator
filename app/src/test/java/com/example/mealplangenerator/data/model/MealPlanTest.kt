package com.example.mealplangenerator.data.model

import com.example.mealplangenerator.data.model.meal.MainDish
import com.example.mealplangenerator.data.model.meal.Meal
import com.example.mealplangenerator.data.model.mealplan.MealPlan
import com.example.mealplangenerator.data.model.mealplan.slot.Slot
import com.example.mealplangenerator.enums.MealTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.DayOfWeek

class MealPlanTest {
    private lateinit var plan: MealPlan

    @Before
    fun setUp()
    {
        plan = MealPlan()
    }

    @Test
    fun newMealPlan_ContainsAnEmptyMealsCollection()
    {
        assertTrue(plan.isEmpty())
    }

    @Test
    fun afterAddMeal_isNotEmpty()
    {
        plan[Slot(DayOfWeek.MONDAY, MealTime.LUNCH)] = Meal(MainDish("Meal 1"))
        assertFalse(plan.isEmpty())
    }

    @Test
    fun overridePreviousMeal_whenAddingMealInSameSlot()
    {
        val mondayLunch = Slot(DayOfWeek.MONDAY, MealTime.LUNCH)
        plan[mondayLunch] = Meal(MainDish("Meal 1"))
        assertEquals("Meal 1", plan[mondayLunch]?.mainDish?.name)
        plan[mondayLunch] = Meal(MainDish("Meal 2"))
        assertEquals("Meal 2", plan[mondayLunch]?.mainDish?.name)
    }

    @Test
    fun overridePreviousMeal_whenAddingMealWithAnEqualsSlot()
    {
        val mondayLunch = Slot(DayOfWeek.MONDAY, MealTime.LUNCH)
        plan[mondayLunch] = Meal(MainDish("Meal 1"))
        assertEquals("Meal 1", plan[mondayLunch]?.mainDish?.name)
        plan[Slot(DayOfWeek.MONDAY, MealTime.LUNCH)] = Meal(MainDish("Meal 2"))
        assertEquals("Meal 2", plan[mondayLunch]?.mainDish?.name)
    }

    @Test
    fun getListOfMeal_OrderedBySlotDayAndTime()
    {
        plan[Slot(DayOfWeek.MONDAY, MealTime.DINNER)] = Meal(MainDish("Meal 2"))
        plan[Slot(DayOfWeek.THURSDAY, MealTime.DINNER)] = Meal(MainDish("Meal 4"))
        plan[Slot(DayOfWeek.THURSDAY, MealTime.LUNCH)] = Meal(MainDish("Meal 3"))
        plan[Slot(DayOfWeek.MONDAY, MealTime.LUNCH)] = Meal(MainDish("Meal 1"))

        val meals = plan.getSortedBySlot()
        var key = 0
        meals.forEach{
            key++
            val mealName = "Meal $key"
            assertEquals(mealName, it.value?.mainDish?.name)
        }
    }

    @Test
    fun getSortedListOfMealOfASpecificDay()
    {
        plan[Slot(DayOfWeek.MONDAY, MealTime.DINNER)] = Meal(MainDish("Meal 2"))
        plan[Slot(DayOfWeek.THURSDAY, MealTime.DINNER)] = Meal(MainDish("Meal 4"))
        plan[Slot(DayOfWeek.THURSDAY, MealTime.LUNCH)] = Meal(MainDish("Meal 3"))
        plan[Slot(DayOfWeek.MONDAY, MealTime.LUNCH)] = Meal(MainDish("Meal 1"))

        val meals = plan.getByDay(DayOfWeek.THURSDAY)
        var key = 2
        meals.forEach{
            key++
            val mealName = "Meal $key"
            assertEquals(mealName, it.value?.mainDish?.name)
        }
    }

    @Test
    fun aSlotCanBeEmpty()
    {
        plan[Slot(DayOfWeek.WEDNESDAY, MealTime.LUNCH)] = null
    }

}