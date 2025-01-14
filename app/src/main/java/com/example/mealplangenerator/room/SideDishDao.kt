package com.example.mealplangenerator.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SideDishDao {
    @Query("SELECT * FROM side_dishes")
    fun getAll(): List<SideDish>

    @Query("SELECT * FROM side_dishes WHERE id IN (:sideDishIds)")
    fun loadAllByIds(sideDishIds: IntArray): List<SideDish>

    @Query("SELECT * FROM side_dishes WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): SideDish

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(sideDish: SideDish)

    @Insert
    fun insertAll(vararg sideDishes: SideDish)

    @Delete
    fun delete(sideDish: SideDish)

    @Query("DELETE FROM side_dishes")
    suspend fun deleteAll()
}