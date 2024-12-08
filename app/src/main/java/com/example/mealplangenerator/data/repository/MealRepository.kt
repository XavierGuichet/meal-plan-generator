package com.example.mealplangenerator.data.repository

import com.example.mealplangenerator.data.model.Meal

class MealRepository() {
    val fakeMeals: MutableCollection<Meal> = mutableSetOf<Meal>()

    val myMeal: Meal = Meal("Cordon bleu")

    init {
        val myMeal1: Meal = Meal("Cordon bleu")
        fakeMeals.add(myMeal1)
        val myMeal2: Meal = Meal("Poulet Tikka Massala")
        fakeMeals.add(myMeal2)


        println ("Today is $myMeal1.name and the fish eat $myMeal2.name")

    }

    val meals: List<Meal>
        get() = emptyList<Meal>()
}