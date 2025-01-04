package com.example.mealplangenerator.services

import com.example.mealplangenerator.data.model.meal.MainDish
import com.example.mealplangenerator.data.model.meal.Meal
import com.example.mealplangenerator.data.model.mealplan.MealCriteria
import com.example.mealplangenerator.data.repository.MainDishesRepositoryInterface
import com.example.mealplangenerator.enums.MealTime
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class MealFactoryTest {
    class FakeRepository: MainDishesRepositoryInterface {
        var dishList: List<MainDish> = emptyList()
        override fun getByCriteria(mealCriteria: MealCriteria?): List<MainDish> {
            if (mealCriteria == null)
                return dishList
            return dishList.filter { meal -> (meal.mealTime == mealCriteria.mealTime || meal.mealTime == MealTime.ANY) }
        }
    }
    private lateinit var factory: MealFactory
    private var mainDishRepo: FakeRepository = FakeRepository()

    @Before
    fun setUp()
    {
        factory = MealFactory(mainDishRepo)
    }

    @Test
    fun getRandomMeal_returnNull_whenNoMainDishAvailable()
    {
        assertNull(factory.getRandomMeal())
    }

    @Test
    fun getRandomMeal_returnAMeal_WhenAMainDishIsAvailable()
    {
        mainDishRepo.dishList = listOf(
            MainDish("My Dish")
        )
        val meal = factory.getRandomMeal()
        assertTrue(meal is Meal)
        assertEquals("My Dish", meal!!.mainDish.name)
    }

    @Test
    fun getRandomMeal_returnAMealWithRandomDish_WhenMultipleMainDishIsAvailable()
    {
        mainDishRepo.dishList = listOf(
            MainDish("My Dish 1"),
            MainDish("My Dish 2"),
        )
        class FakeRandom: Random() {
            var count = 0
            override fun nextBits(bitCount: Int): Int {
                val bitList = intArrayOf(1,0)
                val nextBit = bitList[count]
                count++
                return nextBit
            }
        }
        val randomGen = FakeRandom()
        factory.random = randomGen

        val meal = factory.getRandomMeal()
        assertTrue(meal is Meal)
        assertEquals("My Dish 2", meal!!.mainDish.name)

        val otherMeal = factory.getRandomMeal()
        assertEquals("My Dish 1", otherMeal!!.mainDish.name)
    }

    @Test
    fun getRandomMeal_returnOnlyMealRespectingCriteria()
    {
        mainDishRepo.dishList = listOf(
            MainDish("My Dish 1", MealTime.DINNER),
            MainDish("My Dish 2", MealTime.LUNCH),
        )
        class FakeRandom: Random() {
            override fun nextBits(bitCount: Int): Int {
                return 0
            }
        }
        val randomGen = FakeRandom()
        factory.random = randomGen

        val meal = factory.getRandomMeal(MealCriteria(MealTime.LUNCH))
        assertEquals("My Dish 2", meal!!.mainDish.name)
    }

}