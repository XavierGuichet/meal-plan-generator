package com.example.mealplangenerator.data.model

import com.example.mealplangenerator.enums.MealTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.DayOfWeek

class MealPlantTest {
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
        plan[MealSlot(DayOfWeek.MONDAY, MealTime.LUNCH)] = Meal(MainDish("Meal 1"))
        assertFalse(plan.isEmpty())
    }

    @Test
    fun overridePreviousMeal_whenAddingMealInSameSlot()
    {
        val mondayLunch = MealSlot(DayOfWeek.MONDAY, MealTime.LUNCH)
        plan[mondayLunch] = Meal(MainDish("Meal 1"))
        assertEquals("Meal 1", plan[mondayLunch]?.mainDish?.name)
        plan[mondayLunch] = Meal(MainDish("Meal 2"))
        assertEquals("Meal 2", plan[mondayLunch]?.mainDish?.name)
    }

    @Test
    fun overridePreviousMeal_whenAddingMealWithAnEqualsSlot()
    {
        val mondayLunch = MealSlot(DayOfWeek.MONDAY, MealTime.LUNCH)
        plan[mondayLunch] = Meal(MainDish("Meal 1"))
        assertEquals("Meal 1", plan[mondayLunch]?.mainDish?.name)
        plan[MealSlot(DayOfWeek.MONDAY, MealTime.LUNCH)] = Meal(MainDish("Meal 2"))
        assertEquals("Meal 2", plan[mondayLunch]?.mainDish?.name)
    }

    @Test
    fun getListOfMeal_OrderedBySlotDayAndTime()
    {
        plan[MealSlot(DayOfWeek.MONDAY, MealTime.DINNER)] = Meal(MainDish("Meal 2"))
        plan[MealSlot(DayOfWeek.THURSDAY, MealTime.DINNER)] = Meal(MainDish("Meal 4"))
        plan[MealSlot(DayOfWeek.THURSDAY, MealTime.LUNCH)] = Meal(MainDish("Meal 3"))
        plan[MealSlot(DayOfWeek.MONDAY, MealTime.LUNCH)] = Meal(MainDish("Meal 1"))

        val meals = plan.getSortedBySlot()
        var key = 0
        meals.forEach{
            key++
            val mealName = "Meal $key"
            assertEquals(mealName, it.value.mainDish.name)
        }
    }

    @Test
    fun getSortedListOfMealOfASpecificDay()
    {
        plan[MealSlot(DayOfWeek.MONDAY, MealTime.DINNER)] = Meal(MainDish("Meal 2"))
        plan[MealSlot(DayOfWeek.THURSDAY, MealTime.DINNER)] = Meal(MainDish("Meal 4"))
        plan[MealSlot(DayOfWeek.THURSDAY, MealTime.LUNCH)] = Meal(MainDish("Meal 3"))
        plan[MealSlot(DayOfWeek.MONDAY, MealTime.LUNCH)] = Meal(MainDish("Meal 1"))

        val meals = plan.getByDay(DayOfWeek.THURSDAY)
        var key = 2
        meals.forEach{
            key++
            val mealName = "Meal $key"
            assertEquals(mealName, it.value.mainDish.name)
        }
    }

}