package com.example.mealplangenerator.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DishDao {
    @Query("SELECT * FROM dishes")
    fun getAll(): List<Dish>

    @Query("SELECT * FROM dishes WHERE uid IN (:dishIds)")
    fun loadAllByIds(dishIds: IntArray): List<Dish>

    @Query("SELECT * FROM dishes WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Dish

    @Insert
    fun insertAll(vararg dishes: Dish)

    @Delete
    fun delete(dish: Dish)
}