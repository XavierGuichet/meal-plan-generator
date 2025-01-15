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


@Database(entities = [MainDish::class, SideDish::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mainDishDao(): MainDishDao?

    private class AppDatabaseCallback(private val scope: CoroutineScope) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    insertMainDishes(database)

                    mainDishDao.deleteAll()

                    mainDishDao.insertAll(
                        MainDish("Cordon bleu", MealTime.LUNCH, Duration.QUICK, 2, "", true, true),
                        MainDish("Pavé de saumon", MealTime.LUNCH, Duration.QUICK, 2, "", true, true),
                        MainDish("Cotes de porc citron", MealTime.LUNCH, Duration.SHORT, 1, "", false, true),
                        MainDish("Salade Caesar", MealTime.ANY, Duration.SHORT),
                        MainDish("Dinde cacahuète / petit légumes", MealTime.ANY, Duration.MEDIUM),
                        MainDish("Filet de dinde aux 3 poivrons", MealTime.ANY, Duration.MEDIUM),
                        MainDish("Bagel saumon citron vert", MealTime.ANY, Duration.MEDIUM),
                        MainDish("Nuggets poulet courgette,", MealTime.ANY, Duration.MEDIUM),
                        MainDish("Steak dinde aux poivrons", MealTime.ANY, Duration.MEDIUM),
                        MainDish("Croquette de haddock", MealTime.ANY, Duration.MEDIUM, 1 , "", false, true),
                        MainDish("Pates", MealTime.ANY, Duration.QUICK),
                        MainDish("Paella", MealTime.ANY, Duration.MEDIUM),
                        MainDish("Riz a l'espagnole", MealTime.LUNCH, Duration.SHORT),
                        MainDish("Curry poulet", MealTime.ANY, Duration.MEDIUM),
                        MainDish("Ragout de patate", MealTime.ANY, Duration.MEDIUM),
                        MainDish("Oeufs champignon", MealTime.ANY, Duration.QUICK),
                        MainDish("Omelette ratatouille", MealTime.ANY, Duration.QUICK),
                        MainDish("Tarte", MealTime.ANY, Duration.MEDIUM),
                        MainDish("Burger", MealTime.ANY, Duration.LONG),
                        MainDish("Burger végétarien", MealTime.ANY, Duration.MEDIUM),
                        MainDish("Galette", MealTime.ANY, Duration.MEDIUM),
                        MainDish("Chili con carne", MealTime.ANY, Duration.MEDIUM),
                        MainDish("Quinoa enchiladas", MealTime.ANY, Duration.MEDIUM),
                        MainDish("Boeuf Korean", MealTime.ANY, Duration.MEDIUM),
                        MainDish("Crevette courgette", MealTime.ANY, Duration.MEDIUM),
                        MainDish("Rouleau de printemps", MealTime.ANY, Duration.MEDIUM),
                        MainDish("Tacos", MealTime.ANY, Duration.MEDIUM),
                        MainDish("Roulés de poulet au jambon cru", MealTime.ANY, Duration.LONG),
                        MainDish("Poulet bacon BBQ", MealTime.ANY, Duration.LONG),
                        MainDish("Croque-monsieur", MealTime.ANY, Duration.QUICK),
                        MainDish("Boeuf mariné au 5 épices", MealTime.DINNER, Duration.MEDIUM),
                        MainDish("Fish n Chips", MealTime.DINNER, Duration.MEDIUM),
                        MainDish("Ravioli", MealTime.DINNER, Duration.MEDIUM),
                        MainDish("Raclette", MealTime.DINNER, Duration.MEDIUM),
                        MainDish("Tartiflette", MealTime.DINNER, Duration.LONG),
                        MainDish("Soupe", MealTime.DINNER, Duration.MEDIUM),
                        MainDish("Fondue de luxe", MealTime.DINNER, Duration.MEDIUM),
                        MainDish("Croissant aux fromages", MealTime.DINNER, Duration.MEDIUM),
                        MainDish("Sushi", MealTime.DINNER, Duration.MEDIUM),
                        MainDish("Mac N Cheese", MealTime.DINNER, Duration.SHORT),
                        MainDish("Lasagne", MealTime.DINNER, Duration.MEDIUM),
                        MainDish("Hachis parmentier", MealTime.DINNER, Duration.LONG),
                        MainDish("Curry de crevette", MealTime.DINNER, Duration.LONG),
                        MainDish("Boeuf bourguignon", MealTime.DINNER, Duration.LONG),
                        MainDish(
                            "Steak haché végétaux",
                            MealTime.LUNCH,
                            Duration.QUICK,
                            1,
                            "Pois chiches|Lentilles|Pois Cassés"
                        ),
                        MainDish(
                            "Couscous",
                            MealTime.ANY,
                            Duration.MEDIUM,
                            1,
                            "Boeuf|Falafel"
                        ),
                        MainDish(
                            "Poulet [du monde]",
                            MealTime.ANY,
                            Duration.MEDIUM,
                            1,
                        "tikka massala|miel et au vinaigre|tandoori|saté"
                        ),
                        MainDish(
                            "Pates",
                            MealTime.DINNER,
                            Duration.MEDIUM,
                            3,
                            "Bolognèse maison|Bolognèse Végé|JFK Pasta|Carbonara|Aux courgettes"
                        ),
                        MainDish(
                            "Pizza",
                            MealTime.DINNER,
                            Duration.MEDIUM,
                            1,
                        "Chorizo|Royale|Bacon|Tartiflette",
                            true
                        ),
                        MainDish(
                            "Risotto",
                            MealTime.DINNER,
                            Duration.MEDIUM,
                            1,
                            "aux champignons|aux légumes|au chorizo")
                        ,
                    )
                }
            }
        }

        private suspend fun insertMainDishes(database: AppDatabase) {
            val mainDishDao = database.mainDishDao()
            if (mainDishDao == null)
                return

            mainDishDao.deleteAll()

            mainDishDao.insertAll(
                MainDish("Cordon bleu", MealTime.LUNCH, Duration.QUICK, 2, "", true, true),
                MainDish("Pavé de saumon", MealTime.LUNCH, Duration.QUICK, 2, "", true, true),
                MainDish(
                    "Cotes de porc citron",
                    MealTime.LUNCH,
                    Duration.SHORT,
                    1,
                    "",
                    false,
                    true
                ),
                MainDish("Salade Caesar", MealTime.ANY, Duration.SHORT),
                MainDish("Dinde cacahuète / petit légumes", MealTime.ANY, Duration.MEDIUM),
                MainDish("Filet de dinde aux 3 poivrons", MealTime.ANY, Duration.MEDIUM),
                MainDish("Bagel saumon citron vert", MealTime.ANY, Duration.MEDIUM),
                MainDish("Nuggets poulet courgette,", MealTime.ANY, Duration.MEDIUM),
                MainDish("Steak dinde aux poivrons", MealTime.ANY, Duration.MEDIUM),
                MainDish("Croquette de haddock", MealTime.ANY, Duration.MEDIUM, 1, "", false, true),
                MainDish("Pates", MealTime.ANY, Duration.QUICK),
                MainDish("Paella", MealTime.ANY, Duration.MEDIUM),
                MainDish("Riz a l'espagnole", MealTime.LUNCH, Duration.SHORT),
                MainDish("Curry poulet", MealTime.ANY, Duration.MEDIUM),
                MainDish("Ragout de patate", MealTime.ANY, Duration.MEDIUM),
                MainDish("Oeufs champignon", MealTime.ANY, Duration.QUICK),
                MainDish("Omelette ratatouille", MealTime.ANY, Duration.QUICK),
                MainDish("Tarte", MealTime.ANY, Duration.MEDIUM),
                MainDish("Burger", MealTime.ANY, Duration.LONG),
                MainDish("Burger végétarien", MealTime.ANY, Duration.MEDIUM),
                MainDish("Galette", MealTime.ANY, Duration.MEDIUM),
                MainDish("Chili con carne", MealTime.ANY, Duration.MEDIUM),
                MainDish("Quinoa enchiladas", MealTime.ANY, Duration.MEDIUM),
                MainDish("Boeuf Korean", MealTime.ANY, Duration.MEDIUM),
                MainDish("Crevette courgette", MealTime.ANY, Duration.MEDIUM),
                MainDish("Rouleau de printemps", MealTime.ANY, Duration.MEDIUM),
                MainDish("Tacos", MealTime.ANY, Duration.MEDIUM),
                MainDish("Roulés de poulet au jambon cru", MealTime.ANY, Duration.LONG),
                MainDish("Poulet bacon BBQ", MealTime.ANY, Duration.LONG),
                MainDish("Croque-monsieur", MealTime.ANY, Duration.QUICK),
                MainDish("Boeuf mariné au 5 épices", MealTime.DINNER, Duration.MEDIUM),
                MainDish("Fish n Chips", MealTime.DINNER, Duration.MEDIUM),
                MainDish("Ravioli", MealTime.DINNER, Duration.MEDIUM),
                MainDish("Raclette", MealTime.DINNER, Duration.MEDIUM),
                MainDish("Tartiflette", MealTime.DINNER, Duration.LONG),
                MainDish("Soupe", MealTime.DINNER, Duration.MEDIUM),
                MainDish("Fondue de luxe", MealTime.DINNER, Duration.MEDIUM),
                MainDish("Croissant aux fromages", MealTime.DINNER, Duration.MEDIUM),
                MainDish("Sushi", MealTime.DINNER, Duration.MEDIUM),
                MainDish("Mac N Cheese", MealTime.DINNER, Duration.SHORT),
                MainDish("Lasagne", MealTime.DINNER, Duration.MEDIUM),
                MainDish("Hachis parmentier", MealTime.DINNER, Duration.LONG),
                MainDish("Curry de crevette", MealTime.DINNER, Duration.LONG),
                MainDish("Boeuf bourguignon", MealTime.DINNER, Duration.LONG),
                MainDish(
                    "Steak haché végétaux",
                    MealTime.LUNCH,
                    Duration.QUICK,
                    1,
                    "Pois chiches|Lentilles|Pois Cassés"
                ),
                MainDish(
                    "Couscous",
                    MealTime.ANY,
                    Duration.MEDIUM,
                    1,
                    "Boeuf|Falafel"
                ),
                MainDish(
                    "Poulet [du monde]",
                    MealTime.ANY,
                    Duration.MEDIUM,
                    1,
                    "tikka massala|miel et au vinaigre|tandoori|saté"
                ),
                MainDish(
                    "Pates",
                    MealTime.DINNER,
                    Duration.MEDIUM,
                    3,
                    "Bolognèse maison|Bolognèse Végé|JFK Pasta|Carbonara|Aux courgettes"
                ),
                MainDish(
                    "Pizza",
                    MealTime.DINNER,
                    Duration.MEDIUM,
                    1,
                    "Chorizo|Royale|Bacon|Tartiflette",
                    true
                ),
                MainDish(
                    "Risotto",
                    MealTime.DINNER,
                    Duration.MEDIUM,
                    1,
                    "aux champignons|aux légumes|au chorizo"
                ),
            )
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