package com.example.mealplangenerator.data.repository

import com.example.mealplangenerator.enums.Duration
import com.example.mealplangenerator.data.model.MainDish
import com.example.mealplangenerator.data.model.MealCriteria
import com.example.mealplangenerator.enums.MealTime

class MainDishesRepository() {
    private val mainDishes: List<MainDish>
        get() = mutableListOf<MainDish>(
            MainDish("Cordon bleu", MealTime.LUNCH, Duration.QUICK, 2),
            MainDish("Pavé de saumon", MealTime.LUNCH, Duration.QUICK),

            MainDish("Couscous", MealTime.ANY, Duration.MEDIUM),
            MainDish("Boulette de boeuf / couscous", MealTime.ANY, Duration.MEDIUM),
            MainDish("Paella",MealTime.ANY, Duration.MEDIUM),
            MainDish("Riz a l'espagnole", MealTime.LUNCH, Duration.SHORT),
            MainDish("Curry poulet", MealTime.ANY, Duration.MEDIUM),
            MainDish("Poulet Tikka Massala", MealTime.ANY, Duration.MEDIUM),
            MainDish("Ragout de patate", MealTime.ANY, Duration.MEDIUM),
            MainDish("Mac N Cheese", MealTime.ANY, Duration.SHORT),
            MainDish("Oeufs champignon", MealTime.ANY, Duration.QUICK),
            MainDish("Salade Ceasar", MealTime.ANY, Duration.SHORT),
            MainDish("Omelette ratatouille", MealTime.ANY, Duration.QUICK),
            MainDish("Tarte", MealTime.ANY, Duration.MEDIUM),
            MainDish("Burger", MealTime.ANY, Duration.LONG),
            MainDish("Burger végétarien", MealTime.ANY, Duration.MEDIUM),
            MainDish("Steak haché végétaux", MealTime.ANY, Duration.QUICK),
            MainDish("Steack pois chiches", MealTime.ANY, Duration.MEDIUM),
            MainDish("Croquette de hadock", MealTime.ANY, Duration.QUICK),
            MainDish("Dinde cacahuète / petit légumes", MealTime.ANY, Duration.MEDIUM),
            MainDish("Filet de dinde aux 3 poivrons", MealTime.ANY, Duration.MEDIUM),
            MainDish("Pilons de vollaile épicés", MealTime.ANY, Duration.MEDIUM),
            MainDish("Steack dinde mais poivron", MealTime.ANY, Duration.MEDIUM),
            MainDish("Bagel saumon citron vert", MealTime.ANY, Duration.MEDIUM),
            MainDish("Croque monsieur", MealTime.ANY, Duration.MEDIUM),
            MainDish("Lasagne", MealTime.ANY, Duration.MEDIUM),
            MainDish("Galette", MealTime.ANY, Duration.MEDIUM),
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
            MainDish("Ravioli", MealTime.DINNER, Duration.MEDIUM),
            MainDish("Raclette", MealTime.DINNER, Duration.MEDIUM),
            MainDish("Tartiflette", MealTime.DINNER, Duration.MEDIUM),
            MainDish("Hachis parmentier", MealTime.DINNER, Duration.MEDIUM),
            MainDish("Soupe", MealTime.DINNER, Duration.MEDIUM),
            MainDish("Curry de crevette", MealTime.DINNER, Duration.MEDIUM),
            MainDish("Fondue de luxe", MealTime.DINNER, Duration.MEDIUM),
            MainDish("Boeuf bourguignon", MealTime.DINNER, Duration.MEDIUM),
            MainDish("Croissant aux fromages", MealTime.DINNER, Duration.MEDIUM),
            MainDish("Sushi", MealTime.DINNER, Duration.MEDIUM),
            MainDish("Pates", MealTime.DINNER, Duration.QUICK, 3, arrayOf("Bolognèse maison", "Bolognèse Végé", "JFK Pasta")),
            MainDish("Pizza", MealTime.DINNER, Duration.MEDIUM, 1, arrayOf("Chorizo", "Royale", "Bacon", "Tartiflette")),
            MainDish("Risotto", MealTime.DINNER, Duration.LONG, 1, arrayOf("aux champignons", "aux légumes", "au chorizo")),
        )

    fun getByCriteria(mealCriteria: MealCriteria?): List<MainDish> {
        var validDishes = mainDishes
        if (mealCriteria !== null)
            validDishes = filterDishesByCriteria(validDishes, mealCriteria)

        return validDishes
    }

    private fun filterDishesByCriteria(validDishes: List<MainDish>, mealCriteria: MealCriteria ): List<MainDish> {
        var filteredDishes = validDishes.filter { meal -> (meal.mealTime == mealCriteria.mealTime || meal.mealTime == MealTime.ANY) }
        filteredDishes = filteredDishes.filter { meal -> meal.preparationDuration == mealCriteria.maxPreparationDuration } // TODO() update to deal with max concept
        return filteredDishes
    }
}