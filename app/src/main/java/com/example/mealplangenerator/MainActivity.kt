package com.example.mealplangenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mealplangenerator.data.model.meal.MainDish
import com.example.mealplangenerator.enums.MealTime
import com.example.mealplangenerator.ui.MealPlanViewModel
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
fun WeekMealPlan(modifier: Modifier = Modifier, mealPlanViewModel: MealPlanViewModel = viewModel())
{
    val mealPlanUiState by mealPlanViewModel.uiState.collectAsState()
    val mealPlan = mealPlanUiState.weeklyMealPlan.mealPlan
    Column (modifier = modifier) {
        Card(modifier = Modifier.fillMaxHeight(0.8f))
        {
            mealPlan.entries.forEach {
                DayComponent(it.key, it.value)
            }
        }

        Button(onClick = {mealPlanViewModel.generateMealPlan() }) {
            Text(stringResource(R.string.regenerate_plan))
        }
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

