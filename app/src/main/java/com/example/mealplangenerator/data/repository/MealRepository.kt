package com.example.mealplangenerator.data.repository

import com.example.mealplangenerator.enums.Duration
import com.example.mealplangenerator.data.model.Meal
import com.example.mealplangenerator.data.model.MealTimeParams
import com.example.mealplangenerator.enums.MealTime

class MealRepository() {
    private val meals: List<Meal>
        get() = mutableListOf<Meal>(
            Meal("Cordon bleu", MealTime.LUNCH, Duration.QUICK),
            Meal("Pavé de saumon", MealTime.LUNCH, Duration.QUICK),
            Meal("Poulet Tikka Massala", MealTime.ANY, Duration.MEDIUM),
            Meal("Couscous", MealTime.ANY, Duration.MEDIUM),
            Meal("Paella",MealTime.ANY, Duration.MEDIUM),
            Meal("Curry poulet", MealTime.ANY, Duration.MEDIUM),
            Meal("Ragout de patate", MealTime.ANY, Duration.MEDIUM),
            Meal("JFK Pasta", MealTime.ANY, Duration.SHORT),
            Meal("Mac N Cheese", MealTime.ANY, Duration.SHORT),
            Meal("Oeufs champignon", MealTime.ANY, Duration.QUICK),
            Meal("Riz a l'espagnole", MealTime.ANY, Duration.SHORT),
            Meal("Couscous", MealTime.ANY, Duration.SHORT),
            Meal("Salade Ceasar", MealTime.ANY, Duration.SHORT),
            Meal("Omelette ratatouille", MealTime.ANY, Duration.QUICK),
            Meal("Tarte", MealTime.ANY, Duration.MEDIUM),
            Meal("Burger", MealTime.ANY, Duration.LONG),
            Meal("Steak haché végétaux", MealTime.ANY, Duration.QUICK),
            Meal("Croquette de hadock", MealTime.ANY, Duration.QUICK),
            Meal("Dinde cacahuète / petit légumes", MealTime.ANY),
            Meal("Filet de dinde aux 3 poivrons", MealTime.ANY),
            Meal("Pilons de vollaile épicés", MealTime.ANY),
            Meal("Steack dinde mais poivron", MealTime.ANY),
            Meal("Burger végétarien", MealTime.ANY),
            Meal("Steack pois chiches", MealTime.ANY),
            Meal("Bagel saumon citron vert", MealTime.ANY),
            Meal("Croque monsieur", MealTime.ANY),
            Meal("Lasagne", MealTime.ANY),
            Meal("Galette", MealTime.ANY),
            Meal("Boulette de boeuf / couscous", MealTime.ANY),
            Meal("Chili con carne", MealTime.ANY),
            Meal("Boeuf mariné au cinq épices", MealTime.ANY),
            Meal("Cotes de porc citron", MealTime.ANY),
            Meal("Poulet au miel et au vinaigre", MealTime.ANY),
            Meal("Poulet tandoori", MealTime.ANY),
            Meal("Pate au courgette", MealTime.ANY),
            Meal("Roulés de poulet au jambon cru", MealTime.ANY),
            Meal("Poulet bacon BBQ", MealTime.ANY),
            Meal("Quinoa enchiladas", MealTime.ANY),
            Meal("Poulet tikka massala", MealTime.ANY),
            Meal("Boeuf Korean", MealTime.ANY),
            Meal("Crevette courgette", MealTime.ANY),
            Meal("Rouleau de printemps", MealTime.ANY),
            Meal("Nuggets poulets courgette,", MealTime.ANY),
            Meal("Tacos", MealTime.ANY),
            Meal("Quinoa Cheddar", MealTime.ANY),
            Meal("Fish n Chips", MealTime.DINNER),
            Meal("Raclette", MealTime.DINNER),
            Meal("Ravioli", MealTime.DINNER),
            Meal("Raclette", MealTime.DINNER),
            Meal("Tartiflette", MealTime.DINNER),
            Meal("Hachis parmentier", MealTime.DINNER),
            Meal("Pates", MealTime.DINNER, Duration.QUICK, arrayOf("Bolognèse maison", "Bolognèse Végé")),
            Meal("Pizza", MealTime.DINNER, Duration.MEDIUM, arrayOf("Chorizo", "Royale", "Bacon", "Tartiflette")),
            Meal("Soupe", MealTime.DINNER),
            Meal("Curry de crevette", MealTime.DINNER),
            Meal("Fondue de luxe", MealTime.DINNER),
            Meal("Boeuf bourguignon", MealTime.DINNER),
            Meal("Risotto", MealTime.ANY, Duration.LONG, arrayOf("aux champignons", "aux légumes", "au chorizo")),
            Meal("Croissant aux fromages", MealTime.DINNER),
            Meal("Sushi", MealTime.DINNER),
        )

    fun getOneForConfig(lunchMealConfig: MealTimeParams?): Meal {
        var validMeals = meals
        if (lunchMealConfig !== null) {
            validMeals = meals.filter { meal -> (meal.mealTime == lunchMealConfig.mealTime || meal.mealTime == MealTime.ANY) }
            validMeals = validMeals.filter { meal -> meal.preparationDuration == lunchMealConfig.maxPreparationDuration } // TODO() update to deal with max concept
        }

        return validMeals.random()
    }
}