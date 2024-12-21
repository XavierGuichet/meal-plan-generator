package com.example.mealplangenerator.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mealplangenerator.enums.Duration
import com.example.mealplangenerator.enums.MealTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [Dish::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dishDao(): DishDao?

    private class AppDatabaseCallback(private val scope: CoroutineScope) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val dishDao = database.dishDao()
                    if (dishDao == null)
                        return@launch

                    dishDao.deleteAll()

                    dishDao.insertAll(
                        Dish("Cordon bleu", MealTime.LUNCH, Duration.QUICK, 2),
                        Dish("Pavé de saumon", MealTime.LUNCH, Duration.QUICK),
                        Dish("Cotes de porc citron", MealTime.LUNCH, Duration.SHORT),
                        Dish("Salade Caesar", MealTime.ANY, Duration.SHORT),
                        Dish("Dinde cacahuète / petit légumes", MealTime.ANY, Duration.MEDIUM),
                        Dish("Filet de dinde aux 3 poivrons", MealTime.ANY, Duration.MEDIUM),
                        Dish("Bagel saumon citron vert", MealTime.ANY, Duration.MEDIUM),
                        Dish("Nuggets poulet courgette,", MealTime.ANY, Duration.MEDIUM),
                        Dish("Steak dinde aux poivrons", MealTime.ANY, Duration.MEDIUM),
                        Dish("Croquette de haddock", MealTime.ANY, Duration.MEDIUM),
                        Dish("Pates", MealTime.ANY, Duration.QUICK),
                        Dish("Paella", MealTime.ANY, Duration.MEDIUM),
                        Dish("Riz a l'espagnole", MealTime.LUNCH, Duration.SHORT),
                        Dish("Curry poulet", MealTime.ANY, Duration.MEDIUM),
                        Dish("Ragout de patate", MealTime.ANY, Duration.MEDIUM),
                        Dish("Oeufs champignon", MealTime.ANY, Duration.QUICK),
                        Dish("Omelette ratatouille", MealTime.ANY, Duration.QUICK),
                        Dish("Tarte", MealTime.ANY, Duration.MEDIUM),
                        Dish("Burger", MealTime.ANY, Duration.LONG),
                        Dish("Burger végétarien", MealTime.ANY, Duration.MEDIUM),
                        Dish("Galette", MealTime.ANY, Duration.MEDIUM),
                        Dish("Chili con carne", MealTime.ANY, Duration.MEDIUM),
                        Dish("Quinoa enchiladas", MealTime.ANY, Duration.MEDIUM),
                        Dish("Boeuf Korean", MealTime.ANY, Duration.MEDIUM),
                        Dish("Crevette courgette", MealTime.ANY, Duration.MEDIUM),
                        Dish("Rouleau de printemps", MealTime.ANY, Duration.MEDIUM),
                        Dish("Tacos", MealTime.ANY, Duration.MEDIUM),
                        Dish("Roulés de poulet au jambon cru", MealTime.ANY, Duration.LONG),
                        Dish("Poulet bacon BBQ", MealTime.ANY, Duration.LONG),
                        Dish("Croque-monsieur", MealTime.ANY, Duration.QUICK),
                        Dish("Boeuf mariné au 5 épices", MealTime.DINNER, Duration.MEDIUM),
                        Dish("Fish n Chips", MealTime.DINNER, Duration.MEDIUM),
                        Dish("Ravioli", MealTime.DINNER, Duration.MEDIUM),
                        Dish("Raclette", MealTime.DINNER, Duration.MEDIUM),
                        Dish("Tartiflette", MealTime.DINNER, Duration.LONG),
                        Dish("Soupe", MealTime.DINNER, Duration.MEDIUM),
                        Dish("Fondue de luxe", MealTime.DINNER, Duration.MEDIUM),
                        Dish("Croissant aux fromages", MealTime.DINNER, Duration.MEDIUM),
                        Dish("Sushi", MealTime.DINNER, Duration.MEDIUM),
                        Dish("Mac N Cheese", MealTime.DINNER, Duration.SHORT),
                        Dish("Lasagne", MealTime.DINNER, Duration.MEDIUM),
                        Dish("Hachis parmentier", MealTime.DINNER, Duration.LONG),
                        Dish("Curry de crevette", MealTime.DINNER, Duration.LONG),
                        Dish("Boeuf bourguignon", MealTime.DINNER, Duration.LONG),
                        Dish(
                            "Steak haché végétaux",
                            MealTime.LUNCH,
                            Duration.QUICK,
                            1
                        ), // , arrayOf("Pois chiches", "Lentilles", "Pois Cassés")),
                        Dish(
                            "Couscous",
                            MealTime.ANY,
                            Duration.MEDIUM,
                            1
                        ), // , arrayOf("Boeuf", "Falafel")),
                        Dish(
                            "Poulet [du monde]",
                            MealTime.ANY,
                            Duration.MEDIUM,
                            1
                        ), // , arrayOf("tikka massala",  "miel et au vinaigre", "tandoori", "saté")),
                        Dish(
                            "Pates",
                            MealTime.DINNER,
                            Duration.MEDIUM,
                            3
                        ), // , arrayOf("Bolognèse maison", "Bolognèse Végé", "JFK Pasta", "Carbonara", "Aux courgettes")),
                        Dish(
                            "Pizza",
                            MealTime.DINNER,
                            Duration.MEDIUM,
                            1
                        ), // , arrayOf("Chorizo", "Royale", "Bacon", "Tartiflette")),
                        Dish(
                            "Risotto",
                            MealTime.DINNER,
                            Duration.MEDIUM,
                            1
                        ), // , arrayOf("aux champignons", "aux légumes", "au chorizo")),
                    )
                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mpg_db"
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}