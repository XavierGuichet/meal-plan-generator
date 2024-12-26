package com.example.mealplangenerator.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mealplangenerator.data.model.MealCriteria
import com.example.mealplangenerator.data.model.WeeklyMealPlan
import com.example.mealplangenerator.data.repository.MainDishesRepository
import com.example.mealplangenerator.enums.Duration
import com.example.mealplangenerator.enums.MealTime
import com.example.mealplangenerator.room.AppDatabase
import com.example.mealplangenerator.services.MealPlanFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.DayOfWeek

class MealPlanViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(MealPlanUiState())
    val uiState: StateFlow<MealPlanUiState> = _uiState.asStateFlow()

    private lateinit var currentWeeklyMealPlan: WeeklyMealPlan

    private fun getAppDataBase(): AppDatabase {
        val applicationScope = CoroutineScope(SupervisorJob())
        val context = getApplication<Application>().applicationContext
        return AppDatabase.getDatabase(
            context,
            applicationScope
        )
    }

    fun generateMealPlan(): WeeklyMealPlan {
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
        val db = getAppDataBase()
        val dishesRepository = MainDishesRepository(db)
        val mealPlanFactory = MealPlanFactory(dishesRepository)
        currentWeeklyMealPlan = mealPlanFactory.makePlanForOneWeek(mealPlanCriteria)

        updateMealPlan()
        return currentWeeklyMealPlan
    }

    private fun updateMealPlan() {
        _uiState.update {
            currentState -> currentState.copy(weeklyMealPlan = currentWeeklyMealPlan)
        }
    }
}