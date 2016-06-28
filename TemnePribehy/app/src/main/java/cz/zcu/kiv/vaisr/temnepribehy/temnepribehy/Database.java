package cz.zcu.kiv.vaisr.temnepribehy.temnepribehy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Třída pro komunikaci s databází
 * Jde o jedináčka
 */
public class Database extends SQLiteOpenHelper {

    public static Database INSTANCE = null;

    private static final String DB_NAME = "DarkStoriesDB.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_TEXTS = "texts";
    public static final String TABLE_STATS = "stats";


    private static final String CREATE_TABLE_TEXTS = "CREATE TABLE "+TABLE_TEXTS+
                                                        " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                        " title TEXT UNIQUE," +
                                                        " text TEXT," +
                                                        " solution TEXT," +
                                                        " imgType INT NOT NULL," +
                                                        " imgStory TEXT NOT NULL," +
                                                        " imgSolution TEXT NOT NULL);";
    private static final String CREATE_TABLE_STATS = "CREATE TABLE "+ TABLE_STATS +
            " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " game INT NOT NULL," +
            " yes INT," +
            " no INT," +
            " time INT," +
            "FOREIGN KEY(game) REFERENCES "+TABLE_TEXTS+"(_id)" +
            ");";

    private static final String DELETE_TABLE_TEXT = "DROP TABLE IF EXISTS " + TABLE_TEXTS + ";";
    private static final String DELETE_TABLE_STATS = "DROP TABLE IF EXISTS " + TABLE_STATS + ";";

    private static final String CREATE_ALL_TABLES = CREATE_TABLE_TEXTS + CREATE_TABLE_STATS;
    private static final String DELETE_ALL_TABLES = DELETE_TABLE_TEXT + DELETE_TABLE_STATS;


    //==============================================================================================


    private Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static void setUpDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = new Database(context);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        AppStatus.INSTANCE.resetStoryNumber();
        AppStatus.INSTANCE.save();

        Log.i("TemnePribehy", "Database.onCreate(): Vytvarim databazi.");
        db.execSQL(CREATE_ALL_TABLES);

        ContentValues cv = new ContentValues();
        cv.put("title", "Romeo a Julie");
        cv.put("text", "Leželi na podlaze a oba byli mrtví.");
        cv.put("solution", "Oknem vklezla kočka do pokoje, skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.");
        cv.put("imgType", "1");
        cv.put("imgStory", R.drawable.image_story_romeo);
        cv.put("imgSolution", R.drawable.image_solution_romeo);
        db.insert(TABLE_TEXTS, null, cv);

        cv.put("title", "Medovina");
        cv.put("text", "Krátký úvod, dlouhý konec");
        cv.put("solution", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam erat volutpat. Phasellus enim erat, vestibulum vel, aliquam a, posuere eu, velit. Morbi imperdiet, mauris ac auctor dictum, nisl ligula egestas nulla, et sollicitudin sem purus in lacus. Proin mattis lacinia justo. Nullam faucibus mi quis velit. Aenean placerat. In convallis. Nulla pulvinar eleifend sem. Integer tempor. Nam sed tellus id magna elementum tincidunt. Phasellus faucibus molestie nisl. Nulla turpis magna, cursus sit amet, suscipit a, interdum id, felis.");
        cv.put("imgType", "1");
        cv.put("imgStory", R.drawable.image_story_medovina);
        cv.put("imgSolution", R.drawable.image_solution_medovina);
        db.insert(TABLE_TEXTS, null, cv);

        cv.put("title", "Opravdu dlouhá příběh, který bude dělat paseku");
        cv.put("text", "Tento příběh má opravdu dlouhé všechny texty. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam erat volutpat. Phasellus enim erat, vestibulum vel, aliquam a, posuere eu, velit. Morbi imperdiet, mauris ac auctor dictum, nisl ligula egestas nulla, et sollicitudin sem purus in lacus. Proin mattis lacinia justo. Nullam faucibus mi quis velit. Aenean placerat. In convallis. Nulla pulvinar eleifend sem. Integer tempor. Nam sed tellus id magna elementum tincidunt. Phasellus faucibus molestie nisl. Nulla turpis magna, cursus sit amet, suscipit a, interdum id, felis. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam erat volutpat. Phasellus enim erat, vestibulum vel, aliquam a, posuere eu, velit. Morbi imperdiet, mauris ac auctor dictum, nisl ligula egestas nulla, et sollicitudin sem purus in lacus. Proin mattis lacinia justo. Nullam faucibus mi quis velit. Aenean placerat. In convallis. Nulla pulvinar eleifend sem. Integer tempor. Nam sed tellus id magna elementum tincidunt. Phasellus faucibus molestie nisl. Nulla turpis magna, cursus sit amet, suscipit a, interdum id, felis. KONEC");
        cv.put("solution", "I popis řešení je dlouhý. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam erat volutpat. Phasellus enim erat, vestibulum vel, aliquam a, posuere eu, velit. Morbi imperdiet, mauris ac auctor dictum, nisl ligula egestas nulla, et sollicitudin sem purus in lacus. Proin mattis lacinia justo. Nullam faucibus mi quis velit. Aenean placerat. In convallis. Nulla pulvinar eleifend sem. Integer tempor. Nam sed tellus id magna elementum tincidunt. Phasellus faucibus molestie nisl. Nulla turpis magna, cursus sit amet, suscipit a, interdum id, felis. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam erat volutpat. Phasellus enim erat, vestibulum vel, aliquam a, posuere eu, velit. Morbi imperdiet, mauris ac auctor dictum, nisl ligula egestas nulla, et sollicitudin sem purus in lacus. Proin mattis lacinia justo. Nullam faucibus mi quis velit. Aenean placerat. In convallis. Nulla pulvinar eleifend sem. Integer tempor. Nam sed tellus id magna elementum tincidunt. Phasellus faucibus molestie nisl. Nulla turpis magna, cursus sit amet, suscipit a, interdum id, felis. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam erat volutpat. Phasellus enim erat, vestibulum vel, aliquam a, posuere eu, velit. Morbi imperdiet, mauris ac auctor dictum, nisl ligula egestas nulla, et sollicitudin sem purus in lacus. Proin mattis lacinia justo. Nullam faucibus mi quis velit. Aenean placerat. In convallis. Nulla pulvinar eleifend sem. Integer tempor. Nam sed tellus id magna elementum tincidunt. Phasellus faucibus molestie nisl. Nulla turpis magna, cursus sit amet, suscipit a, interdum id, felis. KONEC");
        cv.put("imgType", "1");
        cv.put("imgStory", R.drawable.image_story_dlouhy);
        cv.put("imgSolution", R.drawable.image_solution_dlouhy);
        db.insert(TABLE_TEXTS, null, cv);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("TemnePribehy", "Database().onUpgrade()");
        db.execSQL(DELETE_ALL_TABLES);
        onCreate(db);
    }

    public long insertTexts(ContentValues content){
        Log.i("TemnePribehy", "Database().insertTexts() - start");
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_TEXTS, null, content);
    }
    public long insertStats(ContentValues content){
        Log.i("TemnePribehy", "Database().insertStats() - start");
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_STATS, null, content);
    }
}
