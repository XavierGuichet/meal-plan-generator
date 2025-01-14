package com.example.mealplangenerator.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "side_dishes")
class SideDish(
    @ColumnInfo(name = "name") val name: String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}