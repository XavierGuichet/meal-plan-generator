package com.example.mealplangenerator.data.repository

import com.example.mealplangenerator.data.model.meal.SideDish
import com.example.mealplangenerator.room.AppDatabase

class SideDishRepository(private val db: AppDatabase) {
    private var all = mutableListOf<SideDish>()
    private var initialized =  false

    private fun init()
    {
        val dbSideDishes = db.sideDishDao()?.getAll()
        dbSideDishes?.forEach {
            val sideDish = SideDish(it.name)
            all.add(sideDish)
        }

        initialized = true
    }

    fun getAll(): MutableList<SideDish> {
        if (!initialized)
            init()

        return all
    }
}