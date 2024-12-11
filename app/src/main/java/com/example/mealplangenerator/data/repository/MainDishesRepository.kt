package com.example.mealplangenerator.data.repository

import com.example.mealplangenerator.enums.Duration
import com.example.mealplangenerator.data.model.MainDish
import com.example.mealplangenerator.data.model.MealCriteria
import com.example.mealplangenerator.enums.MealTime

class MainDishesRepository() {
    private val mainDishes: List<MainDish>
        get() = mutableListOf<MainDish>(
            MainDish("Cordon bleu", MealTime.LUNCH, Duration.QUICK),
            MainDish("Pavé de saumon", MealTime.LUNCH, Duration.QUICK),
            MainDish("Poulet Tikka Massala", MealTime.ANY, Duration.MEDIUM),
            MainDish("Couscous", MealTime.ANY, Duration.MEDIUM),
            MainDish("Paella",MealTime.ANY, Duration.MEDIUM),
            MainDish("JFK Pasta", MealTime.ANY, Duration.SHORT),
            MainDish("Curry poulet", MealTime.ANY, Duration.MEDIUM),
            MainDish("Ragout de patate", MealTime.ANY, Duration.MEDIUM),
            MainDish("Mac N Cheese", MealTime.ANY, Duration.SHORT),
            MainDish("Oeufs champignon", MealTime.ANY, Duration.QUICK),
            MainDish("Riz a l'espagnole", MealTime.ANY, Duration.SHORT),
            MainDish("Couscous", MealTime.ANY, Duration.SHORT),
            MainDish("Salade Ceasar", MealTime.ANY, Duration.SHORT),
            MainDish("Omelette ratatouille", MealTime.ANY, Duration.QUICK),
            MainDish("Tarte", MealTime.ANY, Duration.MEDIUM),
            MainDish("Burger", MealTime.ANY, Duration.LONG),
            MainDish("Steak haché végétaux", MealTime.ANY, Duration.QUICK),
            MainDish("Croquette de hadock", MealTime.ANY, Duration.QUICK),
            MainDish("Dinde cacahuète / petit légumes", MealTime.ANY, Duration.MEDIUM),
            MainDish("Filet de dinde aux 3 poivrons", MealTime.ANY, Duration.MEDIUM),
            MainDish("Pilons de vollaile épicés", MealTime.ANY, Duration.MEDIUM),
            MainDish("Steack dinde mais poivron", MealTime.ANY, Duration.MEDIUM),
            MainDish("Burger végétarien", MealTime.ANY, Duration.MEDIUM),
            MainDish("Steack pois chiches", MealTime.ANY, Duration.MEDIUM),
            MainDish("Bagel saumon citron vert", MealTime.ANY, Duration.MEDIUM),
            MainDish("Croque monsieur", MealTime.ANY, Duration.MEDIUM),
            MainDish("Lasagne", MealTime.ANY, Duration.MEDIUM),
            MainDish("Galette", MealTime.ANY, Duration.MEDIUM),
            MainDish("Boulette de boeuf / couscous", MealTime.ANY, Duration.MEDIUM),
            MainDish("Chili con carne", MealTime.ANY, Duration.MEDIUM),
            MainDish("Boeuf mariné au cinq épices", MealTime.ANY, Duration.MEDIUM),
            MainDish("Cotes de porc citron", MealTime.ANY, Duration.MEDIUM),
            MainDish("Poulet au miel et au vinaigre", MealTime.ANY, Duration.MEDIUM),
            MainDish("Poulet tandoori", MealTime.ANY, Duration.MEDIUM),
            MainDish("Pate au courgette", MealTime.ANY, Duration.MEDIUM),
            MainDish("Roulés de poulet au jambon cru", MealTime.ANY, Duration.MEDIUM),
            MainDish("Poulet bacon BBQ", MealTime.ANY, Duration.MEDIUM),
            MainDish("Quinoa enchiladas", MealTime.ANY, Duration.MEDIUM),
            MainDish("Poulet tikka massala", MealTime.ANY, Duration.MEDIUM),
            MainDish("Boeuf Korean", MealTime.ANY, Duration.MEDIUM),
            MainDish("Crevette courgette", MealTime.ANY, Duration.MEDIUM),
            MainDish("Rouleau de printemps", MealTime.ANY, Duration.MEDIUM),
            MainDish("Nuggets poulets courgette,", MealTime.ANY, Duration.MEDIUM),
            MainDish("Tacos", MealTime.ANY, Duration.MEDIUM),
            MainDish("Quinoa Cheddar", MealTime.ANY, Duration.MEDIUM),
            MainDish("Fish n Chips", MealTime.DINNER, Duration.MEDIUM),
            MainDish("Raclette", MealTime.DINNER, Duration.MEDIUM),
            MainDish("Ravioli", MealTime.DINNER, Duration.MEDIUM),
            MainDish("Raclette", MealTime.DINNER, Duration.MEDIUM),
            MainDish("Tartiflette", MealTime.DINNER, Duration.MEDIUM),
            MainDish("Hachis parmentier", MealTime.DINNER, Duration.MEDIUM),
            MainDish("Pates", MealTime.DINNER, Duration.QUICK, arrayOf("Bolognèse maison", "Bolognèse Végé")),
            MainDish("Pizza", MealTime.DINNER, Duration.MEDIUM, arrayOf("Chorizo", "Royale", "Bacon", "Tartiflette")),
            MainDish("Soupe", MealTime.DINNER, Duration.MEDIUM),
            MainDish("Curry de crevette", MealTime.DINNER, Duration.MEDIUM),
            MainDish("Fondue de luxe", MealTime.DINNER, Duration.MEDIUM),
            MainDish("Boeuf bourguignon", MealTime.DINNER, Duration.MEDIUM),
            MainDish("Risotto", MealTime.ANY, Duration.LONG, arrayOf("aux champignons", "aux légumes", "au chorizo")),
            MainDish("Croissant aux fromages", MealTime.DINNER, Duration.MEDIUM),
            MainDish("Sushi", MealTime.DINNER, Duration.MEDIUM),
        )

    fun getOneForCriteria(lunchMealConfig: MealCriteria?): MainDish {
        var validDishes = mainDishes
        if (lunchMealConfig !== null) {
            validDishes = mainDishes.filter { meal -> (meal.mealTime == lunchMealConfig.mealTime || meal.mealTime == MealTime.ANY) }
            validDishes = validDishes.filter { meal -> meal.preparationDuration == lunchMealConfig.maxPreparationDuration } // TODO() update to deal with max concept
        }

        return validDishes.random()
    }
}