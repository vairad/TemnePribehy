package cz.zcu.kiv.vaisr.temnepribehy.temnepribehy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by olie on 24.5.16.
 */
public class Database extends SQLiteOpenHelper {

    public static Database INSTANCE = null;

    private static final String DB_NAME = "DarkStoriesDB.db";
    private static final int DB_VERSION = 6;

    public static final String TABLE_TEXTS = "texts";
    public static final String TABLE_STATS = "stats";


    private static final String CREATE_TABLE_TEXTS = "CREATE TABLE "+TABLE_TEXTS+
                                                        " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                        " title TEXT UNIQUE," +
                                                        " text TEXT," +
                                                        " solution TEXT);";
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

    private Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static void setUpDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = new Database(context);
        }else{
            return;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i("TemnePribehy", "Database.onCreate(): Vytvarim databazi.");
        db.execSQL(CREATE_ALL_TABLES);

        ContentValues cv = new ContentValues();
        cv.put("title", "Romeo a Julie");
        cv.put("text","Leželi na podlaze a oba byli mrtví.");
        cv.put("solution", "Oknem vklezla kočka do pokoje, skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.");
        db.insert(TABLE_TEXTS, null, cv);

        cv.put("title", "Medovina");
        cv.put("text", "Tento příběh má opravdu dlouhé řešení");
        cv.put("solution","Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.");
        db.insert(TABLE_TEXTS, null, cv);

        cv.put("title", "Opravdu dlouhá příběh, který bude dělat paseku");
        cv.put("text", "Tento příběh má opravdu dlouhé včechno Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.");
        cv.put("solution","Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.Oknem vklezla kočka do pokoje,  skočila na akvárium, to se převrhlo a rozbilo. Ano, Romeo a Julie byly akvarijní rybičky které zemřely po rozbití akvária.");
        db.insert(TABLE_TEXTS, null, cv);

        Status.INSTANCE.resetStoryNumber();
        Status.INSTANCE.save();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_ALL_TABLES);
        onCreate(db);

    }

    public long insertTexts(ContentValues content){
        Log.i("TemnePribehy", "Database().insertTexts() - start");
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_TEXTS, null, content);
    }
}
