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
import com.example.mealplangenerator.data.model.MealTimeParams
import com.example.mealplangenerator.data.repository.MealRepository
import com.example.mealplangenerator.enums.Duration
import com.example.mealplangenerator.enums.MealTime
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
    val weekDays = setOf(
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY,
        DayOfWeek.SATURDAY,
        DayOfWeek.SUNDAY,
    )

    val mealPlanConfigurations = setOf(
        MealTimeParams(DayOfWeek.MONDAY, MealTime.LUNCH, Duration.SHORT),
        MealTimeParams(DayOfWeek.MONDAY, MealTime.DINNER, Duration.MEDIUM),
        MealTimeParams(DayOfWeek.TUESDAY, MealTime.LUNCH, Duration.SHORT),
        MealTimeParams(DayOfWeek.TUESDAY, MealTime.DINNER, Duration.MEDIUM),
        MealTimeParams(DayOfWeek.WEDNESDAY, MealTime.LUNCH, Duration.SHORT),
        MealTimeParams(DayOfWeek.WEDNESDAY, MealTime.DINNER, Duration.MEDIUM),
        MealTimeParams(DayOfWeek.THURSDAY, MealTime.LUNCH, Duration.SHORT),
        MealTimeParams(DayOfWeek.THURSDAY, MealTime.DINNER, Duration.MEDIUM),
        MealTimeParams(DayOfWeek.FRIDAY, MealTime.LUNCH, Duration.SHORT),
        MealTimeParams(DayOfWeek.FRIDAY, MealTime.DINNER, Duration.MEDIUM),
        MealTimeParams(DayOfWeek.SATURDAY, MealTime.LUNCH, Duration.SHORT),
        MealTimeParams(DayOfWeek.SATURDAY, MealTime.DINNER, Duration.MEDIUM),
        MealTimeParams(DayOfWeek.SUNDAY, MealTime.LUNCH, Duration.SHORT),
        MealTimeParams(DayOfWeek.SUNDAY, MealTime.DINNER, Duration.MEDIUM),
    )

    Card(modifier = modifier)
    {
        for (weekDay in weekDays)
        {
            val lunchMealConfig = mealPlanConfigurations.find { config -> (config.dayOfWeek != weekDay && config.mealTime != MealTime.LUNCH)}
            val dinnerMealConfig = mealPlanConfigurations.find { config -> (config.dayOfWeek != weekDay && config.mealTime != MealTime.DINNER)}
            val lunchMeal = mr.getOneForConfig(lunchMealConfig)
            val dinnerMeal = mr.getOneForConfig(dinnerMealConfig)
            DayComponent(weekDay, lunchMeal, dinnerMeal)
        }
    }
}

@Composable
fun DayComponent(day: DayOfWeek, lunchMeal: Meal, dinnerMeal: Meal)
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

