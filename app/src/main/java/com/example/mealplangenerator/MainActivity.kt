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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.example.mealplangenerator.data.model.MainDish
import com.example.mealplangenerator.data.model.MealCriteria
import com.example.mealplangenerator.data.repository.MainDishesRepository
import com.example.mealplangenerator.enums.Duration
import com.example.mealplangenerator.enums.MealTime
import com.example.mealplangenerator.room.AppDatabase
import com.example.mealplangenerator.services.MealPlanFactory
import com.example.mealplangenerator.ui.theme.MealPlanGeneratorTheme
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = getAppDataBase()
        val dishesRepository = MainDishesRepository(db)
        val mealPlanFactory = MealPlanFactory(dishesRepository)
        val mealPlan = generateMealPlan(mealPlanFactory)

        enableEdgeToEdge()
        setContent {
            MealPlanGeneratorTheme {
                MealPlanGeneratorApp(mealPlan)
            }
        }
    }

    private fun generateMealPlan(mealPlanFactory: MealPlanFactory): HashMap<DayOfWeek, HashMap<MealTime, MainDish?>> {
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
        return mealPlanFactory.makePlanForOneWeek(mealPlanCriteria)
    }

    private fun getAppDataBase(): AppDatabase {
        return Room.databaseBuilder(applicationContext,
            AppDatabase::class.java, "mpg-db").build()
    }
}

@Composable
fun WeekMealPlan(mealPlan: HashMap<DayOfWeek, HashMap<MealTime, MainDish?>>,modifier: Modifier = Modifier)
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
fun MealCard(meal: MainDish?, modifier: Modifier = Modifier)
{
    var mealName = "---"
    if (meal != null)
    {
        mealName = meal.name
        if (meal.variation.isNotEmpty()) {
            mealName += " " + meal.variation.random()
        }
    }

    Card(modifier = modifier) {
        Column {
            Text(
                text = mealName,
                fontSize = 14.sp,
                lineHeight = 21.sp,
                modifier = modifier.padding(6.dp)
            )
        }
    }
}

@Composable
private fun MealPlanGeneratorApp(mealPlan: HashMap<DayOfWeek, HashMap<MealTime, MainDish?>>)
{
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        WeekMealPlan(mealPlan,
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

