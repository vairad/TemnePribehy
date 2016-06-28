package cz.zcu.kiv.vaisr.temnepribehy.temnepribehy;

import android.content.ContentValues;
import android.util.Log;

/**
 * Created by olie on 28.6.16.
 */
public class Stats {
    private final int game;
    private final int yes;
    private final int no;
    private final int time;

    public Stats(int game, int yes, int no, int time) {
        this.game = game;
        this.yes = yes;
        this.no = no;
        this.time = time;
    }

    public void saveToDB(){
            Log.i("TemnePribehy", "Story.saveToDB() " + game + " " + yes + " " + no + " " + time);

            ContentValues cv = new ContentValues();
            cv.put("game", game);
            cv.put("yes", yes);
            cv.put("no", no);
            cv.put("time", time);

            Log.i("TemnePribehy", "Story.saveToDB() - before insert ");
            Database.INSTANCE.insertStats(cv);
    }

    @Override
    public String toString(){
        return "Stats: game: "+ game +" yes: "+ yes +" no: "+ no +" time: "+time;
    }
}

