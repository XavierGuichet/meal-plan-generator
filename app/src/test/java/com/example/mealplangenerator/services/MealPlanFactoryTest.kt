package com.example.mealplangenerator.services

import com.example.mealplangenerator.data.model.MainDish
import com.example.mealplangenerator.data.model.MealCriteria
import com.example.mealplangenerator.data.model.MealPlan
import com.example.mealplangenerator.data.repository.MainDishesRepositoryInterface
import org.junit.Assert.assertTrue
import org.junit.Test

class MealPlanFactoryTest() {

    @Test
    fun testAreWorking() {
        assertTrue(true)
    }

    @Test
    fun returnOnListOfMeal()
    {
        class FakeRepository: MainDishesRepositoryInterface {
            override fun getByCriteria(mealCriteria: MealCriteria?): List<MainDish> {
                TODO("Not yet implemented")
            }
        }

        val mpf = MealPlanFactory(FakeRepository())
        val mealPlan = mpf.makeMealPlan()
        assertTrue(mealPlan is MealPlan)
    }
}
