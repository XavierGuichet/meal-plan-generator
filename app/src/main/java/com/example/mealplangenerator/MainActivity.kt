package com.example.mealplangenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mealplangenerator.data.model.Meal
import com.example.mealplangenerator.data.repository.MealRepository
import com.example.mealplangenerator.ui.theme.MealPlanGeneratorTheme
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MealPlanGeneratorTheme {
                MealPlanGeneratorApp()
            }
        }
    }
}

@Composable
fun WeekMealPlan(modifier: Modifier = Modifier)
{
    val mr = MealRepository()
    val lunchMeals = mr.getLunchMeals()
    val dinnerMeals = mr.getDinnerMeals()
    val weekDays = setOf(
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY,
        DayOfWeek.SATURDAY,
        DayOfWeek.SUNDAY,
    )
    Card(modifier = modifier)
    {
        for (weekDay in weekDays)
        {
            val lunchMeal = lunchMeals.random()
            val dinnerMeal = dinnerMeals.random()
            DayComponent(weekDay, lunchMeal, dinnerMeal)
        }
    }
}

@Composable
fun DayComponent(day: DayOfWeek, lunchMeal: Meal, dinnerMeal: Meal, modifier: Modifier = Modifier)
{
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = day.getDisplayName(TextStyle.FULL, Locale.FRANCE),
            fontSize = 14.sp,
            lineHeight = 21.sp,
            fontWeight = FontWeight.Bold,
        )
        Row {
            Column(modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Midi",
                    fontSize = 14.sp,
                    lineHeight = 21.sp
                )
                MealCard(lunchMeal)
            }
            Column(modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Soir",
                    fontSize = 14.sp,
                    lineHeight = 21.sp
                )
                MealCard(dinnerMeal)
            }
        }
    }
}

@Composable
fun MealCard(meal: Meal, modifier: Modifier = Modifier)
{
    Card(modifier = modifier) {
        Column {
            Text(
                text = meal.name,
                fontSize = 12.sp,
                lineHeight = 21.sp,
                modifier = modifier.padding(6.dp)
            )
        }
    }
}

@Preview
@Composable
private fun MealPlanGeneratorApp()
{
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        WeekMealPlan(
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

