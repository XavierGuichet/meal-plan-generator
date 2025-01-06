package com.example.mealplangenerator.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mealplangenerator.data.model.mealplan.MealPlanCriteria
import com.example.mealplangenerator.data.model.mealplan.MealSlot
import com.example.mealplangenerator.data.model.mealplan.WeeklyMealPlan
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
        val weeklyMealPlan = WeeklyMealPlan()
        val mealPlanCriteria = MealPlanCriteria(weeklyMealPlan)
        mealPlanCriteria[MealSlot(DayOfWeek.MONDAY, MealTime.LUNCH)]?.setDuration(Duration.SHORT)
        mealPlanCriteria[MealSlot(DayOfWeek.MONDAY, MealTime.DINNER)]?.setDuration(Duration.MEDIUM)
        mealPlanCriteria[MealSlot(DayOfWeek.TUESDAY, MealTime.LUNCH)]?.setDuration(Duration.SHORT)
        mealPlanCriteria[MealSlot(DayOfWeek.TUESDAY, MealTime.DINNER)]?.setDuration(Duration.MEDIUM)
        mealPlanCriteria[MealSlot(DayOfWeek.WEDNESDAY, MealTime.LUNCH)]?.setDuration(Duration.SHORT)
        mealPlanCriteria[MealSlot(DayOfWeek.WEDNESDAY, MealTime.DINNER)]?.setDuration(Duration.MEDIUM)
        mealPlanCriteria[MealSlot(DayOfWeek.THURSDAY, MealTime.LUNCH)]?.setDuration(Duration.SHORT)
        mealPlanCriteria[MealSlot(DayOfWeek.THURSDAY, MealTime.DINNER)]?.setDuration(Duration.MEDIUM)
        mealPlanCriteria[MealSlot(DayOfWeek.FRIDAY, MealTime.LUNCH)]?.setDuration(Duration.SHORT)
        mealPlanCriteria[MealSlot(DayOfWeek.FRIDAY, MealTime.DINNER,)]?.setDuration(Duration.MEDIUM)
        mealPlanCriteria[MealSlot(DayOfWeek.SATURDAY, MealTime.LUNCH)]?.setDuration(Duration.SHORT)
        mealPlanCriteria[MealSlot(DayOfWeek.SATURDAY, MealTime.DINNER,)]?.setDuration(Duration.MEDIUM)
        mealPlanCriteria[MealSlot(DayOfWeek.SUNDAY, MealTime.LUNCH)]?.setDuration(Duration.SHORT)
        mealPlanCriteria[MealSlot(DayOfWeek.SUNDAY, MealTime.DINNER)]?.setDuration(Duration.MEDIUM)

        val db = getAppDataBase()
        val dishesRepository = MainDishesRepository(db)
        val mealPlanFactory = MealPlanFactory(dishesRepository)
        currentWeeklyMealPlan = mealPlanFactory.makeWeeklyMealPlan(mealPlanCriteria)

        updateMealPlan()
        return currentWeeklyMealPlan
    }

    private fun updateMealPlan() {
        _uiState.update {
            currentState -> currentState.copy(weeklyMealPlan = currentWeeklyMealPlan)
        }
    }
}