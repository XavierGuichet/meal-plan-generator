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
import com.example.mealplangenerator.data.model.MainDish
import com.example.mealplangenerator.data.model.MealCriteria
import com.example.mealplangenerator.enums.Duration
import com.example.mealplangenerator.enums.MealTime
import com.example.mealplangenerator.services.MealPlanFactory
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
    val weekDays = setOf(
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY,
        DayOfWeek.SATURDAY,
        DayOfWeek.SUNDAY,
    )
    val mealPlanCriteria = setOf(
        MealCriteria(DayOfWeek.MONDAY, MealTime.LUNCH, Duration.SHORT),
        MealCriteria(DayOfWeek.MONDAY, MealTime.DINNER, Duration.MEDIUM),
        MealCriteria(DayOfWeek.TUESDAY, MealTime.LUNCH, Duration.SHORT),
        MealCriteria(DayOfWeek.TUESDAY, MealTime.DINNER, Duration.MEDIUM),
        MealCriteria(DayOfWeek.WEDNESDAY, MealTime.LUNCH, Duration.SHORT),
        MealCriteria(DayOfWeek.WEDNESDAY, MealTime.DINNER, Duration.MEDIUM),
        MealCriteria(DayOfWeek.THURSDAY, MealTime.LUNCH, Duration.SHORT),
        MealCriteria(DayOfWeek.THURSDAY, MealTime.DINNER, Duration.MEDIUM),
        MealCriteria(DayOfWeek.FRIDAY, MealTime.LUNCH, Duration.SHORT),
        MealCriteria(DayOfWeek.FRIDAY, MealTime.DINNER, Duration.MEDIUM),
        MealCriteria(DayOfWeek.SATURDAY, MealTime.LUNCH, Duration.SHORT),
        MealCriteria(DayOfWeek.SATURDAY, MealTime.DINNER, Duration.MEDIUM),
        MealCriteria(DayOfWeek.SUNDAY, MealTime.LUNCH, Duration.SHORT),
        MealCriteria(DayOfWeek.SUNDAY, MealTime.DINNER, Duration.MEDIUM)
    )

    val mealPlan = MealPlanFactory().makePlanForOneWeek(mealPlanCriteria)

    Card(modifier = modifier)
    {
        for(day in weekDays)
            DayComponent(day, mealPlan[day])
    }
}

@Composable
fun DayComponent(day: DayOfWeek, dayMealPlan: HashMap<MealTime, MainDish?>?)
{
    val lunchMeal = dayMealPlan?.get(MealTime.LUNCH)
    val dinnerMeal = dayMealPlan?.get(MealTime.DINNER)

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
                if (lunchMeal != null)
                    MealCard(lunchMeal)
            }
            Column(modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Soir",
                    fontSize = 14.sp,
                    lineHeight = 21.sp
                )
                if (dinnerMeal != null)
                    MealCard(dinnerMeal)
            }
        }
    }
}

@Composable
fun MealCard(meal: MainDish, modifier: Modifier = Modifier)
{
    Card(modifier = modifier) {
        Column {
            Text(
                text = meal.name,
                fontSize = 14.sp,
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

