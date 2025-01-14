package com.example.mealplangenerator.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MainDishDao {
    @Query("SELECT * FROM main_dishes")
    fun getAll(): List<MainDish>

    @Query("SELECT * FROM main_dishes WHERE id IN (:mainDishIds)")
    fun loadAllByIds(mainDishIds: IntArray): List<MainDish>

    @Query("SELECT * FROM main_dishes WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): MainDish

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(mainDish: MainDish)

    @Insert
    fun insertAll(vararg mainDishes: MainDish)

    @Delete
    fun delete(mainDish: MainDish)

    @Query("DELETE FROM main_dishes")
    suspend fun deleteAll()
}