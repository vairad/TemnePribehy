package cz.zcu.kiv.vaisr.temnepribehy.temnepribehy;

import android.content.ContentValues;
import android.util.Log;

/**
 * Přepravka pro informace o příběhu.
 */
public class Story {
    private final String title;
    private final String story;
    private final String solution;
    private final String imgHint;
    private final String imgSol;

    public Story(String title, String story, String solution, String imgHint, String imgSol) {
        this.title = title;
        this.story = story;
        this.solution = solution;
        this.imgHint = imgHint;
        this.imgSol = imgSol;
    }

    public void saveToDB(){
        Log.i("TemnePribehy", "Story.saveToDB() " + title + " " + story + " " + solution);

        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("text", story);
        cv.put("solution",solution);

        cv.put("imgType", "3");
        cv.put("imgStory", imgHint);
        cv.put("imgSolution", imgSol);

        Log.i("TemnePribehy", "Story.saveToDB() - before insert ");
        Database.INSTANCE.insertTexts(cv);
    }

    @Override
    public String toString() {
        return "Story{" +
                "title='" + title + '\'' +
                ", story='" + story + '\'' +
                ", solution='" + solution + '\'' +
                ", imgHint='" + imgHint + '\'' +
                ", imgSol='" + imgSol + '\'' +
                '}';
    }
}
